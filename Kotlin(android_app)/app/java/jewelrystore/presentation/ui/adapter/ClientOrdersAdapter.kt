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
import com.sysoliatina.jewelrystore.common.showIf
import com.sysoliatina.jewelrystore.databinding.ItemClientOrderBinding
import com.sysoliatina.jewelrystore.databinding.ItemModeratorOrderBinding
import com.sysoliatina.jewelrystore.databinding.ItemProductBinding
import com.sysoliatina.jewelrystore.models.Order
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.models.enums.OrderStatus
import com.sysoliatina.jewelrystore.models.enums.OrderStatus.Companion.getStatuses


class ClientOrdersAdapter(private val listener: OnClickListener) :
    RecyclerView.Adapter<ClientOrdersAdapter.CourseViewHolder>() {

    private var dataList = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemClientOrderBinding
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
                listener.onOrderClick(order.products)
            }
            binding.btnCancel.setOnClickListener {
                listener.onCancelClick(order)
            }
            binding.btnCancel.showIf(order.status == OrderStatus.IN_PROGRESS)
            val context = itemView.context
            binding.tvTitle.text = context.getString(R.string.order_id, order.id)
            binding.tvOrderStatus.text = context.getString(R.string.order_status, order.status)
            binding.tvUserEmail.text = context.getString(R.string.user_email, order.userEmail)
            binding.tvUserName.text = context.getString(R.string.user_name, order.userName)
            binding.tvOrderDate.text = context.getString(R.string.order_date, order.orderDate)
            binding.tvOrderPrice.text = context.getString(R.string.order_price, order.orderPrice)
        }
    }

    inner class CourseViewHolder(val binding: ItemClientOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onCancelClick(order: Order)
        fun onOrderClick(products: List<Product>)
    }
}