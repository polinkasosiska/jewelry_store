package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentOrdersBinding
import com.sysoliatina.jewelrystore.models.Order
import com.sysoliatina.jewelrystore.models.Product
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ModeratorOrdersAdapter
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.OrderedProductsDialogFragment
import com.sysoliatina.jewelrystore.presentation.viewmodels.ModeratorOrdersViewModel


@AndroidEntryPoint
class ModeratorOrdersFragment : Fragment(R.layout.fragment_orders) {

    private val viewModel by viewModels<ModeratorOrdersViewModel>()
    private val binding by viewBinding(FragmentOrdersBinding::bind)
    private lateinit var adapter: ModeratorOrdersAdapter

    companion object {
        const val KEY_SHOW_ORDERED_PRODUCTS = "showOrderedProducts"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ModeratorOrdersAdapter(object: ModeratorOrdersAdapter.OnSelectedListener {
            override fun onStatusSelected(order: Order) {
                confirmAction(R.string.update_status_request) { _, _ ->
                    viewModel.updateOrderStatus(order.id, order.status)
                }
            }

            override fun onOrderSelected(products: List<Product>) {
                OrderedProductsDialogFragment
                    .newInstance(products as ArrayList<Product>)
                    .show(childFragmentManager, KEY_SHOW_ORDERED_PRODUCTS)
            }

        })
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<Order>)
        }
    }
}