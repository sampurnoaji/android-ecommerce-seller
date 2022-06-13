package id.io.android.olebsai.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemOrderBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.util.addThousandSeparator

class OrderListAdapter : ListAdapter<Order, OrderListAdapter.ViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            with(binding) {
                tvId.apply {
                    text = order.id
                    setBackgroundColor(ContextCompat.getColor(context, order.status.color))
                }
                tvCustomerName.text = order.customerName
                tvDate.text = order.date
                tvTotal.text = "Rp ${order.total.addThousandSeparator()}"

                when (order.products.size) {
                    0 -> {
                    }
                    1 -> {
                        tvProducts.text = "${order.products[0].qty} x ${order.products[0].name}"
                    }
                    2 -> {
                        tvProducts.text = "${order.products[0].qty} x ${order.products[0].name}" +
                                "\n${order.products[1].qty} x ${order.products[1].name}"
                    }
                    else -> {
                        tvProducts.text = "${order.products[0].qty} x ${order.products[0].name}" +
                                "\n${order.products[1].qty} x ${order.products[1].name}"
                        tvOtherProducts.apply {
                            isVisible = true
                            text = context.resources.getString(
                                R.string.x_other_item,
                                order.products.size - 2
                            )
                        }
                    }
                }
            }
        }
    }

    object OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }
    }
}