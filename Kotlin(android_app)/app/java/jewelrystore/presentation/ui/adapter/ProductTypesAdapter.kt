package com.sysoliatina.jewelrystore.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sysoliatina.jewelrystore.databinding.ItemProductTypeBinding
import com.sysoliatina.jewelrystore.models.ProductType


class ProductTypesAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<ProductTypesAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<ProductType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemProductTypeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun setDataList(dataList: MutableList<ProductType>) {
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
            val productType = dataList[position]
            binding.tvTitle.text = productType.name
            binding.btnDelete.setOnClickListener {
                listener.invoke(productType.id)
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root)
}