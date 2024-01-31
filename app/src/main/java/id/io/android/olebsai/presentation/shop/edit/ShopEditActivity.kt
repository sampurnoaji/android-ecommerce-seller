package id.io.android.olebsai.presentation.shop.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityShopEditBinding
import id.io.android.olebsai.domain.model.shop.ShopDetail
import id.io.android.olebsai.domain.model.user.Address
import id.io.android.olebsai.presentation.shop.ShopViewModel
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class ShopEditActivity : BaseActivity<ActivityShopEditBinding, ShopEditViewModel>() {

    override val binding by viewBinding(ActivityShopEditBinding::inflate)
    override val vm: ShopEditViewModel by viewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private var shop: ShopDetail? = null
    private var subDistricts = mutableListOf<Address>()

    private val courierListAdapter by lazy { SelectCourierListAdapter() }

    companion object {
        @JvmStatic
        fun start(context: Context, launcher: ActivityResultLauncher<Intent>) {
            launcher.launch(Intent(context, ShopEditActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupView()
        observeViewModel()

        vm.getCouriers()
    }

    private fun initView() {
        shop = shopViewModel.getShopCached()
        shop?.let { shop ->
            vm.getSubDistricts(shop.districtId)
            with(binding) {
                etShopName.setText(shop.name)
                etNote.setText(shop.note)
                etAddress.setText(shop.address)
                etProvince.setText(shop.province)
                etDistrict.setText(shop.district)
                etSubDistrict.setText(shop.subDistrict)
                etPostalCode.setText(shop.postalCode)
            }
        }
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.profile_edit)
        }

        with(binding.etSubDistrict) {
            setOnClickListener { (it as AutoCompleteTextView).showDropDown() }
            setOnItemClickListener { _, _, index, _ ->
                shop = shop?.copy(
                    subDistrictId = subDistricts[index].id,
                    subDistrict = subDistricts[index].name
                )
            }
        }

        with(binding.rvCouriers) {
            adapter = courierListAdapter
        }

        binding.btnEdit.setOnClickListener {
            validateInput()
        }
    }

    private fun observeViewModel() {
        vm.editShopResult.observe(
            onLoading = {},
            onSuccess = {
                showInfoDialog(it.orEmpty(), positiveAction = {
                    setResult(Activity.RESULT_OK)
                    finish()
                })
            },
            onError = {
                showInfoDialog(it?.message.toString())
            }
        )

        vm.couriersResult.observe(
            onLoading = {},
            onSuccess = { result ->
                result?.let {
                    shop?.let { shop ->
                        courierListAdapter.submitList(it.map { courier ->
                            courier.copy(
                                isSelected = shop.couriers.firstOrNull {
                                    it.kodeKurir == courier.kodeKurir
                                } != null
                            )
                        })
                    }
                }
            },
            onError = {
                showInfoDialog("Gagal memuat Jasa Pengiriman")
            }
        )

        vm.subDistrictsResult.observe(
            onLoading = {},
            onSuccess = { list ->
                list?.let {
                    subDistricts.clear()
                    subDistricts.addAll(it)
                    binding.etSubDistrict.setSimpleItems(it.map { address -> address.name }
                        .toTypedArray())
                }
            },
            onError = {
                showInfoDialog(
                    getString(R.string.profile_edit_error_get_address),
                    positiveText = getString(R.string.try_again),
                    positiveAction = { vm.getSubDistricts(shop?.districtId.orEmpty()) },
                    negativeText = getString(R.string.close),
                    negativeAction = { finish() }
                )
            },
        )
    }

    private fun validateInput() {
        with(binding) {
            val name = etShopName.text
            if (name.isNullOrBlank()) {
                showInfoDialog(getString(R.string.form_fill_x, getString(R.string.shop_name)))
                return
            }
            val note = etNote.text
            if (note.isNullOrBlank()) {
                showInfoDialog(getString(R.string.form_fill_x, getString(R.string.shop_note)))
                return
            }
            val address = etAddress.text
            if (address.isNullOrBlank()) {
                showInfoDialog(getString(R.string.form_fill_x, getString(R.string.address)))
                return
            }
            if (etSubDistrict.text.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.address_sub_district)
                    )
                )
                return
            }
            val postalCode = etPostalCode.text
            if (postalCode.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.address_postal_code)
                    )
                )
                return
            }
            val selectedCourier = courierListAdapter.currentList.firstOrNull { it.isSelected }
            if (selectedCourier == null) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.courier)
                    )
                )
                return
            }

            val bank = etBank.text
            if (bank.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.bank_name)
                    )
                )
                return
            }

            val bankAccountNumber = etBankAccountNumber.text
            if (bankAccountNumber.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.bank_account_number)
                    )
                )
                return
            }

            val bankAccountName = etBankAccountName.text
            if (bankAccountName.isNullOrBlank()) {
                showInfoDialog(
                    getString(
                        R.string.form_fill_x,
                        getString(R.string.bank_account_name)
                    )
                )
                return
            }

            shop?.let { shop ->
                vm.editShop(
                    shop.copy(
                        name = name.toString(),
                        note = note.toString(),
                        address = address.toString(),
                        postalCode = postalCode.toString(),
                        couriers = courierListAdapter.currentList.filter { it.isSelected },
                        bank = bank.toString(),
                        noRekening = bankAccountNumber.toString(),
                        namaPemilikRekening = bankAccountName.toString(),
                    )
                )
            }
        }
    }
}