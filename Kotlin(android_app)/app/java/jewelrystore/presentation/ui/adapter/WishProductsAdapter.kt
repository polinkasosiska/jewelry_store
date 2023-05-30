package com.sysoliatina.jewelrystore.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.databinding.ItemClientProductBinding
import com.sysoliatina.jewelrystore.databinding.ItemProductBinding
import com.sysoliatina.jewelrystore.databinding.ItemWishProductBinding
import com.sysoliatina.jewelrystore.models.Product


class WishProductsAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<WishProductsAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemWishProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun setDataList(dataList: MutableList<Product>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun deleteItem(id: Int) {
        val position = dataList.indexOfFirst { it.id == id }
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val product = dataList[position]
            val context = itemView.context
            binding.tvTitle.text = product.name
            binding.tvPrice.text = context.getString(R.string.price_value, product.price)
            binding.tvMaterial.text = context.getString(R.string.material_value, product.material)
            binding.tvColor.text = context.getString(R.string.color_value, product.color)
            binding.tvCountry.text = context.getString(R.string.country_value, product.country)
            binding.tvWeight.text = context.getString(R.string.weight_value, product.weight)
            binding.tvManufactureDate.text =context.getString(R.string.manufacture_date_value, product.manufactureDate)
            binding.btnAddCartItem.setOnClickListener {
                listener.invoke(product.id)
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemWishProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}