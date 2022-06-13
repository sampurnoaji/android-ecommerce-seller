package id.io.android.olebsai.presentation.product.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.component.StatusChipView
import id.io.android.olebsai.databinding.ItemProductBinding
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.util.toRupiah

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
                imgProduct.load(product.listPicture.firstOrNull()?.url)
                tvName.text = product.namaProduk
                if (product.isHargaPromo) {
                    tvPrice.text = product.hargaPromo.toRupiah()
                    tvOriginalPrice.text = product.hargaNormal.toRupiah()
                    tvPercentDiscount.text = "${product.discount()}%"
                } else {
                    tvPrice.text = product.hargaNormal.toRupiah()
                    tvOriginalPrice.isGone = true
                    tvPercentDiscount.isGone = true
                }
                tvStock.text = product.qtyStock.toString()
                tvSoldCount.text = "Terjual ${product.qtyTerjual}"

                val context = binding.root.context
                containerCategories.apply {
                    removeAllViews()
                    addView(buildCategoryText(context, product.namaKategori))
                    addView(buildCategoryText(context, product.namaSubKategori))
                }

                btnSeeApprovalDetail.isVisible = product.isUpdatingProgress

                when (product.status) {
                    Product.ApprovalStatus.REJECTED -> {
                        status.isVisible = true
                        status.setStatus(
                            StatusChipView.Status.RED,
                            context.resources.getString(R.string.product_approval_rejected)
                        )
                    }

                    Product.ApprovalStatus.REVIEW -> {
                        status.isVisible = true
                        status.setStatus(
                            StatusChipView.Status.BLUE,
                            context.resources.getString(R.string.product_approval_waiting)
                        )
                    }

                    else -> {
                        if (product.isUpdatingProgress) {
                            status.isVisible = true
                            status.setStatus(
                                StatusChipView.Status.GREEN,
                                context.resources.getString(R.string.product_approval_edit_waiting)
                            )
                        }
                    }
                }

                root.setOnClickListener { listener.onItemClicked(product) }
                btnSeeApprovalDetail.setOnClickListener {
                    listener.onSeeApprovalDetailClicked(product)
                }
            }
        }
    }

    object OrderDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.produkId == newItem.produkId
        }
    }

    interface Listener {
        fun onItemClicked(product: Product)
        fun onSeeApprovalDetailClicked(product: Product)
    }
}

fun buildCategoryText(context: Context, category: String): TextView =
    TextView(context).apply {
        setTextAppearance(R.style.Text_Heading7)
        setTextColor(ContextCompat.getColor(context, R.color.blue_gray_50))
        setBackgroundResource(R.color.blue_gray_10)
        setPadding(8, 4, 8, 4)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 8, 0)
        layoutParams = params
        text = category
    }