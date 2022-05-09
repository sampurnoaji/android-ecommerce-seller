package id.io.android.seller.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.seller.databinding.ItemOrderBinding
import id.io.android.seller.domain.model.order.Order
import id.io.android.seller.util.addThousandSeparator
import java.text.NumberFormat
import java.util.Locale

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
                val nf = NumberFormat.getInstance(Locale("id", "ID"))
                tvTotal.text = "Rp ${order.total.addThousandSeparator()}"

                var products = ""
                order.products.onEachIndexed { index, product ->
                    products +=
                        if (index + 1 >= order.products.size) "${product.key} x ${product.value.name}"
                        else "${product.key} x ${product.value.name}\n"
                }
                tvProducts.text = products
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