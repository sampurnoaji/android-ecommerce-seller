package id.io.android.olebsai.presentation.shop.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.databinding.ItemSelectCourierBinding
import id.io.android.olebsai.domain.model.shop.Courier
import id.io.android.olebsai.presentation.shop.edit.SelectCourierListAdapter.ViewHolder

class SelectCourierListAdapter : ListAdapter<Courier, ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSelectCourierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSelectCourierBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(courier: Courier) {
            binding.root.apply {
                text = courier.namaKurir
                isChecked = courier.isSelected

                setOnClickListener {
                    courier.isSelected = !isSelected
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Courier>() {
        override fun areItemsTheSame(oldItem: Courier, newItem: Courier): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Courier, newItem: Courier): Boolean {
            return oldItem.kurirId == newItem.kurirId
        }
    }
}