package id.io.android.olebsai.presentation.order.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityOrderDetailBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.Order.Status
import id.io.android.olebsai.domain.model.order.Order.Status.DIKEMAS
import id.io.android.olebsai.domain.model.order.Order.Status.DITERIMA
import id.io.android.olebsai.presentation.order.history.OrderViewModel
import id.io.android.olebsai.presentation.order.history.setValue
import id.io.android.olebsai.util.toUi
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderViewModel>() {

    override val binding by viewBinding(ActivityOrderDetailBinding::inflate)
    override val vm: OrderViewModel by viewModels()

    private val productListAdapter by lazy { OrderedProductListAdapter() }

    private var headerId = ""

    companion object {
        private const val KEY_HEADER_ID = "headerId"

        @JvmStatic
        fun start(context: Context, headerId: String) {
            val starter = Intent(context, OrderDetailActivity::class.java)
                .putExtra(KEY_HEADER_ID, headerId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()

        headerId = intent.getStringExtra(KEY_HEADER_ID).orEmpty()
        vm.getOrderDetail(headerId)

        observeViewModel()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.order)
        }

        with(binding.rvOrderedProduct) {
            adapter = productListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@OrderDetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeViewModel() {
        vm.orderDetailResult.observe(
            onSuccess = {
                it?.order?.let { order -> setOrder(order) }
                productListAdapter.submitList(it?.orderedProducts)
            },
            onError = {}
        )

        vm.deliverOrderResult.observe(
            onSuccess = {
                showInfoDialog(getString(R.string.order_deliver_success),
                    positiveAction = {
                        vm.getOrderDetail(headerId)
                    })
            },
            onError = {
                showInfoDialog(it?.message ?: getString(R.string.order_deliver_failed))
            }
        )
    }

    private fun setOrder(order: Order) {
        binding.lastStatus.text = order.status.status

        with(binding.sectionOrder) {
            setValue(order)
            tvStatus.isGone = true
            labelOrderDate.isGone = true
            tvOrderDate.isGone = true
        }

        with(binding.sectionProgress) {
            isGone = order.tglCheckout.isEmpty() && order.tglBayar.isEmpty()

            removeAllViews()
            if (order.tglCheckout.isNotEmpty()) {
                addView(
                    ItemTracking(this.context).apply {
                        setHeader(getString(R.string.order_date_checkout))
                        setMessage(order.tglCheckout.toUi())
                    }
                )
            }
            if (order.tglBayar.isNotEmpty()) {
                addView(
                    ItemTracking(this.context).apply {
                        setHeader(getString(R.string.order_date_paid))
                        setMessage(order.tglBayar.toUi())
                    }
                )
            }
        }

        setupButtonAction(order.status, order.headerId)
    }

    private fun setupButtonAction(status: Status, headerId: String) {
        binding.sectionButton.isVisible = status == DIKEMAS
        with(binding.btnAction) {
            when (status) {
                DIKEMAS -> {
                    text = getString(R.string.order_deliver)
                    setOnClickListener {
                        InputNomorResiDialog(this@OrderDetailActivity) { nomorResi ->
                            vm.deliverOrder(headerId, nomorResi)
                        }.show()
                    }
                }

                DITERIMA -> {
                    text = getString(R.string.finish)
                }

                else -> {}
            }
        }
    }
}