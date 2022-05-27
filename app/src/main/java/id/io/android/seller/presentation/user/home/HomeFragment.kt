package id.io.android.seller.presentation.user.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import coil.load
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.seller.R
import id.io.android.seller.core.BaseFragment
import id.io.android.seller.databinding.FragmentHomeBinding
import id.io.android.seller.presentation.event.BannerListAdapter
import id.io.android.seller.util.viewBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModels()

    private val bannerListAdapter by lazy { BannerListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        setupDashBoardCard()
        setupBannerList()

        initProductChart()
        setProductChartData()
    }

    private fun setupToolbar() {
        binding.toolbar.title = "Bejo Shop"
        binding.expandedImage.load("https://picsum.photos/id/1043/5184/3456")
    }

    private fun setupDashBoardCard() {
        binding.card1.setValue("1")
        binding.card2.setValue("3")
        binding.card3.setValue("5")
    }

    private fun setupBannerList() {
        val banners = listOf(
            "https://picsum.photos/id/1043/5184/3456",
            "https://picsum.photos/id/1039/6945/4635",
            "https://picsum.photos/id/1038/3914/5863",
            "https://picsum.photos/id/1037/5760/3840",
        )
        with(binding.vpBanner) {
            adapter = bannerListAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.view_pager_page_margin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.view_pager_page_offset)

            setPageTransformer { page, position ->
                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
        }
        bannerListAdapter.submitList(banners)
    }

    private fun initProductChart() {
        with(binding.productChart) {
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
            "1 Mei 2020" to 14,
            "2 Mei 2020" to 2,
            "3 Mei 2020" to 52,
            "4 Mei 2020" to 29,
            "5 Mei 2020" to 21,
            "6 Mei 2020" to 22,
            "7 Mei 2020" to 120,
        )

        if (soldProducts.isEmpty()) return

        with(binding.productChart) {
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
}