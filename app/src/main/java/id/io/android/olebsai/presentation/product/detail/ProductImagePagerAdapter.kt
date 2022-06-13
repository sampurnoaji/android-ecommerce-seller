package id.io.android.olebsai.presentation.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.databinding.ItemProductImageBinding
import id.io.android.olebsai.domain.model.product.Product.Picture

class ProductImagePagerAdapter(private val listener: Listener) :
    ListAdapter<Picture, ProductImagePagerAdapter.ViewPagerViewHolder>(DIFF_UTIL) {

    inner class ViewPagerViewHolder(val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Picture) {
            binding.imgProduct.load(item.url)
            binding.root.setOnClickListener {
                listener.onClickImage(item)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Picture>() {
            override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
                return oldItem.pictureId == newItem.pictureId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemProductImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface Listener {
        fun onClickImage(picture: Picture)
    }
}