package id.io.olebsai.presentation.product.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.R.string
import id.io.olebsai.core.BaseActivity
import id.io.olebsai.databinding.ActivityProductDetailBinding
import id.io.olebsai.domain.model.product.Product
import id.io.olebsai.domain.model.product.Product.Picture
import id.io.olebsai.presentation.product.image.ProductImagePreviewDialogFragment
import id.io.olebsai.presentation.product.input.ProductInputActivity
import id.io.olebsai.presentation.product.input.ProductInputViewModel
import id.io.olebsai.util.toRupiah
import id.io.olebsai.util.viewBinding

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel>() {

    override val binding by viewBinding(ActivityProductDetailBinding::inflate)
    override val vm: ProductDetailViewModel by viewModels()
    private val productInputViewModel: ProductInputViewModel by viewModels()

    private var productId = ""
    private var productStatus = ""
    private var product: Product? = null

    private var images = listOf<Picture>()
    private val imagesAdapter by lazy { ProductImagePagerAdapter(imageListener) }

    private var imagesCount = 0

    companion object {
        private const val ARG_KEY_ID = "id"
        private const val ARG_KEY_STATUS = "status"

        @JvmStatic
        fun start(
            context: Context,
            launcher: ActivityResultLauncher<Intent>,
            id: String,
            status: String
        ) {
            launcher.launch(Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(ARG_KEY_ID, id)
                putExtra(ARG_KEY_STATUS, status)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        getProductDetail()
        setupActionView()
        setupImagesViewPager()
    }

    private fun setupActionView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(string.product_detail)
            if (productStatus == Product.ApprovalStatus.APPROVE.status) {
                fabAction.apply {
                    isVisible = true
                    setImageResource(R.drawable.baseline_delete_outline_24)
                    setOnClickListener {
                        showInfoDialog(
                            message = getString(string.product_delete_confirmation_message),
                            positiveText = getString(string.cancel),
                            negativeText = getString(string.delete),
                            negativeAction = {
                                vm.deleteProduct(productId)
                            }
                        )
                    }
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            ProductInputActivity.start(this, launcher, productId, productStatus)
        }

        binding.btnAddImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ImageOnly))
        }

        binding.btnUpdateStock.setOnClickListener {
            product?.let {
                val stock = it.qtyStock
                UpdateProductStockDialog(this, stock) { newStock ->
                    productInputViewModel.directUpdateProduct(
                        it.copy(qtyStock = newStock).toUpdateProductParams()
                    )
                }.show()
            }
        }

        binding.btnUpdatePrice.setOnClickListener {
            product?.let { product ->
                UpdateProductPriceDialog(
                    this,
                    product.hargaNormal,
                    product.hargaPromo,
                    product.isHargaPromo
                ) { normalPrice, promoPrice, isActivePromo ->
                    productInputViewModel.directUpdateProduct(
                        product.copy(
                            hargaNormal = normalPrice,
                            hargaPromo = promoPrice,
                            isHargaPromo = isActivePromo,
                        ).toUpdateProductParams()
                    )
                }.show()
            }
        }
    }

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            vm.uploadImage(this, uri, productId)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                getProductDetail()
            }
        }

    private fun setupImagesViewPager() {
        with(binding.vpProduct) {
            adapter = imagesAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private val imageListener = object : ProductImagePagerAdapter.Listener {
        override fun onClickImage(picture: Picture) {
            ProductImagePreviewDialogFragment.showDialog(
                supportFragmentManager,
                picture.url,
                imagePreviewListener
            )
        }
    }

    private val imagePreviewListener = object : ProductImagePreviewDialogFragment.Listener {
        override fun onDeleteClicked(image: String) {
            images.firstOrNull { it.url == image }?.let { picture ->
                vm.deleteImage(picture.pictureId)
            }
        }
    }

    private fun getProductDetail() {
        intent?.let {
            productId = it.getStringExtra(ARG_KEY_ID).orEmpty()
            productStatus = it.getStringExtra(ARG_KEY_STATUS).orEmpty()

            if (productStatus == Product.ApprovalStatus.APPROVE.status) {
                vm.getProductDetail(productId)
                binding.btnEdit.isVisible = true
            } else {
                vm.getProductApprovalDetail(productId)
                binding.btnEdit.isGone = true
            }
        }
    }

    private fun observeViewModel() {
        vm.productDetailResult.observe(
            onLoading = {},
            onSuccess = { product ->
                this.product = product
                product?.let {
                    imagesCount = product.listPicture.size
                    inflateProduct(it)
                }
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )

        vm.uploadImageResult.observe(
            onLoading = {},
            onSuccess = {
                setResult(Activity.RESULT_OK)
                showInfoDialog(getString(string.product_image_add_success))
                vm.getProductDetail(productId)
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )

        vm.deleteImageResult.observe(
            onLoading = {},
            onSuccess = {
                setResult(Activity.RESULT_OK)
                showInfoDialog(getString(string.product_image_delete_success))
                vm.getProductDetail(productId)
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )

        vm.deleteProductResult.observe(
            onLoading = {},
            onSuccess = {
                showInfoDialog(getString(string.product_delete_success),
                    positiveAction = {
                        setResult(Activity.RESULT_OK)
                        finish()
                    })
            },
            onError = {
                showInfoDialog(it?.message ?: getString(string.error))
            }
        )

        productInputViewModel.createProductResult.observe(
            onLoading = {},
            onSuccess = {
                getProductDetail()
                showInfoDialog(it ?: getString(string.product_edit_success))
            },
            onError = {
                showInfoDialog(it?.message.orEmpty())
            }
        )
    }

    private fun inflateProduct(product: Product) {
        with(binding) {
            images = product.listPicture
            imagesAdapter.submitList(images)
            btnAddImage.isVisible = images.size < 3 && productId.isNotEmpty()

            tvName.text = product.namaProduk
            tvPrice.text = product.hargaPromo.toRupiah()
            tvOriginalPrice.text = product.hargaNormal.toRupiah()
            tvPercentDiscount.text = "${product.discount()}%"
            tvPromoStatus.text =
                if (product.isHargaPromo) getString(string.active)
                else getString(string.inactive)
            tvStock.text = product.qtyStock.toString()
            tvSoldCount.text = product.qtyTerjual.toString()

            tvShopName.text = product.namaToko
            tvCategory.text = product.namaKategori
            tvSubCategory.text = product.namaSubKategori
            tvWeight.text = product.beratGram.toString()
            tvDesc.text = product.deskripsi
        }
    }
}