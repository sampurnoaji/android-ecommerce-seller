package id.io.olebsai.presentation.product.input

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R.string
import id.io.olebsai.core.BaseActivity
import id.io.olebsai.databinding.ActivityProductInputBinding
import id.io.olebsai.domain.model.product.Category
import id.io.olebsai.domain.model.product.CreateProductParams
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.model.product.SubCategory
import id.io.olebsai.domain.model.product.UpdateProductParams
import id.io.olebsai.presentation.shop.ShopViewModel
import id.io.olebsai.util.textfield.ThousandTextWatcher
import id.io.olebsai.util.viewBinding
import okhttp3.internal.toLongOrDefault

@AndroidEntryPoint
class ProductInputActivity : BaseActivity<ActivityProductInputBinding, ProductInputViewModel>() {

    override val binding by viewBinding(ActivityProductInputBinding::inflate)
    override val vm: ProductInputViewModel by viewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private var productId: String = ""
    private var subCategoryId: String = ""

    private val categories = mutableListOf<Category>()
    private val subCategories = mutableListOf<SubCategory>()

    private var selectedCategory: Category? = null
    private var selectedSubCategory: SubCategory? = null

    companion object {
        private const val KEY_PRODUCT_ID = "productId"
        private const val KEY_PRODUCT_STATUS = "productStatus"

        @JvmStatic
        fun start(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            productId: String? = null,
            status: String? = null,
        ) {
            launcher.launch(
                Intent(context, ProductInputActivity::class.java)
                    .putExtra(KEY_PRODUCT_ID, productId)
                    .putExtra(KEY_PRODUCT_STATUS, status)

            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        observeViewModel()
        vm.getCategoryList()

        productId = intent.extras?.getString(KEY_PRODUCT_ID).orEmpty()
        if (productId.isNotEmpty()) {
            setupEditProductMode()
        }
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(string.product_add)
        }

        with(binding.etCategory) {
            setOnClickListener { (it as AutoCompleteTextView).showDropDown() }
            setOnItemClickListener { _, _, index, _ ->
                selectedCategory = categories[index]
                selectedCategory?.let {
                    vm.getSubCategoryList(it.kategoriId)
                }
            }
        }

        with(binding.etSubCategory) {
            setOnClickListener { (it as AutoCompleteTextView).showDropDown() }
            setOnItemClickListener { _, _, index, _ ->
                selectedSubCategory = subCategories[index]
            }
        }

        with(binding.etPrice) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        with(binding.etPricePromo) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        with(binding.etStock) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        with(binding.etWeight) {
            addTextChangedListener(ThousandTextWatcher(this))
        }

        binding.btnAdd.setOnClickListener {
            validateInput()
        }
    }

    private fun observeViewModel() {
        vm.productDetailResult.observe(
            onLoading = {},
            onSuccess = { product ->
                product?.let {
                    inflateProduct(it)
                    setCategory(it.kategoriId)
                    subCategoryId = it.subKategoriId
                    vm.getSubCategoryList(it.kategoriId)
                }
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )
        vm.categoryListResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    categories.clear()
                    categories.addAll(it)
                    binding.etCategory.setSimpleItems(it.map { category ->
                        category.namaKategori
                    }.toTypedArray())
                }

                if (productId.isNotEmpty()) {
                    getProductDetail()
                }
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )
        vm.subCategoryListResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    subCategories.clear()
                    subCategories.addAll(it)
                    binding.etSubCategory.setSimpleItems(it.map { subCategory ->
                        subCategory.namaSubKategori
                    }.toTypedArray())
                }

                if (productId.isNotEmpty()) {
                    setSubCategory()
                }
            },
            onError = {}
        )
        vm.createProductResult.observe(
            onLoading = {},
            onSuccess = {
                showInfoDialog(
                    getString(
                        if (productId.isNotEmpty()) string.product_edit_success
                        else string.product_create_success
                    ),
                    positiveAction = {
                        setResult(Activity.RESULT_OK)
                        finish()
                    })
            },
            onError = {
                showInfoDialog(it?.message.orEmpty())
            }
        )
    }

    private fun inflateProduct(product: Product) {
        with(binding) {
            etName.setText(product.namaProduk)
            etCategory.setText(product.namaKategori)
            etSubCategory.setText(product.namaSubKategori)
            etPrice.setText(product.hargaNormal.toString())
            etPricePromo.setText(product.hargaPromo.toString())
            cbIsPromo.isChecked = product.isHargaPromo
            etStock.setText(product.qtyStock.toString())
            etWeight.setText(product.beratGram.toString())
            etDesc.setText(product.deskripsi)
        }
    }

    private fun setupEditProductMode() {
        with(binding) {
            toolbar.tvTitle.text = getString(string.product_edit)
            btnAdd.text = getString(string.product_edit)
            cbIsPromo.isVisible = true
        }
    }

    private fun getProductDetail() {
        intent.extras?.let {
            val status = it.getString(KEY_PRODUCT_STATUS)
            if (status == Product.ApprovalStatus.APPROVE.status) {
                vm.getProductDetail(productId)
            } else vm.getProductApprovalDetail(productId)
        }
    }

    private fun validateInput() {
        with(binding) {
            val name = etName.text
            if (name.isNullOrBlank()) {
                showInfoDialog(getString(string.form_fill_x, getString(string.product_name)))
                return
            }
            val category = etCategory.text
            if (category.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        string.form_fill_x,
                        getString(string.product_category)
                    )
                )
                return
            }
            val subCategory = etSubCategory.text
            if (subCategory.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        string.form_fill_x,
                        getString(string.product_subcategory)
                    )
                )
                return
            }
            val price = etPrice.text
            if (price.isNullOrBlank()) {
                showInfoDialog(getString(string.form_fill_x, getString(string.product_price)))
                return
            }
            val pricePromo = etPricePromo.text
            if (pricePromo.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        string.form_fill_x,
                        getString(string.product_price_promo)
                    )
                )
                return
            }
            val stock = etStock.text
            if (stock.isNullOrBlank()) {
                showInfoDialog(getString(string.form_fill_x, getString(string.product_stock)))
                return
            }
            val desc = etDesc.text
            if (desc.isNullOrBlank()) {
                showInfoDialog(getString(string.form_fill_x, getString(string.product_desc)))
                return
            }
            val weight = etWeight.text
            if (weight.isNullOrBlank()) {
                showInfoDialog(getString(string.form_fill_x, getString(string.product_weight)))
                return
            }

            if (productId.isNotEmpty()) {
                vm.updateProduct(
                    UpdateProductParams(
                        deskripsi = desc.toString().trim(),
                        hargaNormal = price.toString().replace(".", "").toLongOrDefault(0L),
                        hargaPromo = pricePromo.toString().replace(".", "").toLongOrDefault(0L),
                        isHargaPromo = cbIsPromo.isChecked,
                        kategoriId = selectedCategory?.kategoriId.orEmpty(),
                        namaProduk = name.toString().trim(),
                        qtyStock = stock.toString().toIntOrNull() ?: 0,
                        subKategoriId = selectedSubCategory?.subKategoriId.orEmpty(),
                        produkId = productId,
                        beratGram = weight.toString().toIntOrNull() ?: 0,
                    )
                )
            } else {
                vm.createProduct(
                    CreateProductParams(
                        deskripsi = desc.toString().trim(),
                        hargaNormal = price.toString().replace(".", "").toLongOrDefault(0L),
                        hargaPromo = pricePromo.toString().replace(".", "").toLongOrDefault(0L),
                        kategoriId = selectedCategory?.kategoriId.orEmpty(),
                        namaProduk = name.toString().trim(),
                        qtyStock = stock.toString().toIntOrNull() ?: 0,
                        subKategoriId = selectedSubCategory?.subKategoriId.orEmpty(),
                        tokoId = shopViewModel.getShopCached()?.id.orEmpty(),
                        beratGram = weight.toString().toIntOrNull() ?: 0,
                    )
                )
            }
        }
    }

    private fun setCategory(kategoriId: String) {
        selectedCategory = categories.firstOrNull { it.kategoriId == kategoriId }
    }

    private fun setSubCategory() {
        selectedSubCategory = subCategories.firstOrNull { it.subKategoriId == subCategoryId }
    }
}