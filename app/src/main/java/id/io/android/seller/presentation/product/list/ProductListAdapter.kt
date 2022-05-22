package id.io.android.seller.presentation.product.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.seller.databinding.ItemProductBinding
import id.io.android.seller.domain.model.product.Product
import id.io.android.seller.util.addThousandSeparator

class ProductListAdapter(private val listener: Listener) :
    ListAdapter<Product, ProductListAdapter.ViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: Listener) {
            with(binding) {
                imgProduct.load(product.imageUrl)
                tvName.text = product.name
                tvPrice.text = "Rp ${product.price.addThousandSeparator()}"
                tvStock.text = product.stock.toString()

                root.setOnClickListener {
                    listener.onItemClicked(product)
                }
            }
        }
    }

    object OrderDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface Listener {
        fun onItemClicked(product: Product)
    }
}