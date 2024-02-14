package id.io.olebsai.presentation.user.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import id.io.olebsai.R
import id.io.olebsai.core.BaseFragment
import id.io.olebsai.databinding.FragmentHomeBinding
import id.io.olebsai.presentation.event.BannerListAdapter
import id.io.olebsai.presentation.shop.ShopViewModel
import id.io.olebsai.presentation.shop.edit.ShopEditActivity
import id.io.olebsai.presentation.user.login.LoginActivity
import id.io.olebsai.util.dpToPx
import id.io.olebsai.util.ui.Dialog
import id.io.olebsai.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private var carouselJob: Job? = null
    private val bannerListAdapter by lazy { BannerListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel.getShopDetail()

        observeViewModel()

        setupActionView()
        setupDashBoardCard()
        setupBannerCarousel(vm.images)

        initProductChart()
        setProductChartData()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                shopViewModel.getShopDetail()
            }
        }

    private fun observeViewModel() {
        binding.tvUsername.text = getString(R.string.home_hello_username, vm.user?.username)

        shopViewModel.shopDetailResult.observe(
            onLoading = {},
            onSuccess = { shop ->
                shop?.let {
                    binding.tvShop.text = it.name
                    binding.shopStatus.isVisible = !it.isApproved()
                    shopViewModel.saveShop(it)
                }
            },
            onError = {}
        )
    }

    private fun setupActionView() {
        with (binding.root) {
            setOnRefreshListener {
                shopViewModel.getShopDetail()
                isRefreshing = false
            }
        }

        binding.imgLogout.setOnClickListener {
            showLogoutDialog()
        }

        binding.imgEdit.setOnClickListener {
            ShopEditActivity.start(requireContext(), launcher)
        }
    }

    private fun setupDashBoardCard() {
        with(binding.sectionOrder) {
            this.tvOrderNew.text = "0"
            this.tvOrderProcess.text = "0"
            this.tvOrderComplain.text = "0"
            this.tvOrderFinish.text = "0"
        }
    }

    private fun setupBannerCarousel(images: List<Int>) {
        with(binding.vpBanner) {
            adapter = bannerListAdapter
            offscreenPageLimit = 2

            val previewOffsetPx = 30.dpToPx(resources.displayMetrics)
            setPadding(previewOffsetPx, 0, previewOffsetPx, 0)

            val pageMarginPx = 8.dpToPx(resources.displayMetrics)
            val marginTransformer = MarginPageTransformer(pageMarginPx)
            setPageTransformer(marginTransformer)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    carouselJob?.cancel()
                    carouselJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(3000)
                        currentItem =
                            if (position >= images.size - 1) 0
                            else position + 1
                    }
                }
            })
        }
        bannerListAdapter.submitList(images)
    }

    private fun initProductChart() {
        with(binding.sectionChart.productChart) {
            setNoDataText(getString(R.string.empty_data))
            setExtraOffsets(0f, 0f, 0f, 8f)

            axisRight.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            animateX(500, Easing.EaseInSine)

            // to draw label on xAxis
            val xAxis: XAxis = xAxis
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawLabels(true)
            xAxis.granularity = 1f
            xAxis.labelRotationAngle = +90f
        }
    }

    private fun setProductChartData() {
        val soldProducts: Map<String, Int> = mapOf(
            "1 Mei 2024" to 0,
            "2 Mei 2024" to 0,
            "3 Mei 2024" to 0,
            "4 Mei 2024" to 0,
            "5 Mei 2024" to 0,
            "6 Mei 2024" to 0,
            "7 Mei 2024" to 0,
        )

        with(binding.sectionChart.productChart) {
            clear()
            val xLabels = mutableListOf<String>()
            val entries = mutableListOf<Entry>()

            var index = 0f
            for (product in soldProducts) {
                xLabels.add(product.key)
                entries.add(Entry(index, product.value.toFloat()))
                index++
            }

            xAxis.valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val valueIndex = value.toInt()
                    return if (valueIndex < 0 || valueIndex >= xLabels.size) "" else xLabels[valueIndex]
                }
            }

            val dataSet = LineDataSet(entries, "").apply {
                val color = ContextCompat.getColor(context, R.color.green_primary)
                setCircleColor(color)
                this.color = color
                lineWidth = 2f
            }

            data = LineData(dataSet)
            invalidate()
        }
    }

    private fun showLogoutDialog() {
        Dialog(
            context = requireContext(),
            message = getString(R.string.logout_message),
            positiveButtonText = getString(R.string.logout),
            positiveAction = {
                vm.logout()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            },
            negativeButtonText = getString(R.string.cancel)
        ).show()
    }

    override fun onResume() {
        super.onResume()
        carouselJob?.start()
    }

    override fun onPause() {
        super.onPause()
        carouselJob?.cancel()
    }
}