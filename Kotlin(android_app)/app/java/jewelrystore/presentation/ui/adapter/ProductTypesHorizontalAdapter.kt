package com.sysoliatina.jewelrystore.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.databinding.ItemSimpleTextBinding
import com.sysoliatina.jewelrystore.models.ProductsWithType


class ProductTypesHorizontalAdapter(
    private var selectedProductTypePosition: Int,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<ProductTypesHorizontalAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<ProductsWithType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemSimpleTextBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun setDataList(dataList: MutableList<ProductsWithType>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun getDataList(): MutableList<ProductsWithType> {
        return this.dataList
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        with(holder) {
            val productWithType = dataList[position]
            val productType = productWithType.productType
            binding.textView.text = productType
            itemView.setOnClickListener {
                listener.invoke(position)
                selectedProductTypePosition = position
                notifyDataSetChanged()
            }
            if (selectedProductTypePosition == position) {
                binding.textView.setTextColor(itemView.context.getColor(R.color.purple_700))
            } else {
                binding.textView.setTextColor(itemView.context.getColor(android.R.color.tab_indicator_text))
            }
        }
    }

    inner class CourseViewHolder(val binding: ItemSimpleTextBinding) :
        RecyclerView.ViewHolder(binding.root)
}