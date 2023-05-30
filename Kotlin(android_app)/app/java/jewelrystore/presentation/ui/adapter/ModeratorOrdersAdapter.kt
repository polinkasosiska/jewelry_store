package com.sysoliatina.jewelrystore.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.databinding.ItemModeratorOrderBinding
import com.sysoliatina.jewelrystore.databinding.ItemProductBinding
import com.sysoliatina.jewelrystore.models.Order
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import com.sysoliatina.jewelrystore.models.enums.OrderStatus.Companion.getStatuses


class ModeratorOrdersAdapter(private val listener: OnSelectedListener) :
    RecyclerView.Adapter<ModeratorOrdersAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemModeratorOrderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    fun setDataList(dataList: MutableList<Order>) {
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
            val order = dataList[position]
            itemView.setOnClickListener {
                listener.onOrderSelected(order.products)
            }
            val context = itemView.context
            binding.tvTitle.text = context.getString(R.string.order_id, order.id)
            initOrderStatusSelector(binding.orderStatusLayout, order)
            binding.tvUserEmail.text = context.getString(R.string.user_email, order.userEmail)
            binding.tvUserName.text = context.getString(R.string.user_name, order.userName)
            binding.tvOrderDate.text = context.getString(R.string.order_date, order.orderDate)
            binding.tvOrderPrice.text = context.getString(R.string.order_price, order.orderPrice)
        }
    }

    private fun initOrderStatusSelector(statusSelectorLayout: TextInputLayout, order: Order) {
        val items = getStatuses()
        val adapter = ArrayAdapter(statusSelectorLayout.context, R.layout.list_item, items)
        val statusSelectorField = (statusSelectorLayout.editText as? AutoCompleteTextView)
        statusSelectorField?.setText(order.status.toString(), false)
        statusSelectorField?.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                statusSelectorField?.setText(order.status.toString(), false)
                order.status = items[position]
                listener.onStatusSelected(order)
            }
        statusSelectorField?.setAdapter(adapter)
    }

    inner class CourseViewHolder(val binding: ItemModeratorOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnSelectedListener {
        fun onStatusSelected(order: Order)
        fun onOrderSelected(products: List<Product>)
    }
}