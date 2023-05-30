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
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ClientOrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ModeratorOrdersAdapter
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.OrderedProductsDialogFragment
import com.sysoliatina.jewelrystore.presentation.viewmodels.ClientOrdersViewModel
import com.sysoliatina.jewelrystore.presentation.viewmodels.ModeratorOrdersViewModel


@AndroidEntryPoint
class ClientOrdersFragment : Fragment(R.layout.fragment_orders) {

    private val viewModel by viewModels<ClientOrdersViewModel>()
    private val binding by viewBinding(FragmentOrdersBinding::bind)
    private lateinit var adapter: ClientOrdersAdapter

    companion object {
        const val KEY_SHOW_ORDERED_PRODUCTS = "showOrderedProducts"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ClientOrdersAdapter(object: ClientOrdersAdapter.OnClickListener {
            override fun onCancelClick(order: Order) {
                confirmAction(R.string.cancel_request) { _, _ ->
                    viewModel.cancelOrder(order.id)
                }
            }

            override fun onOrderClick(products: List<Product>) {
                OrderedProductsDialogFragment
                    .newInstance(products as ArrayList<Product>)
                    .show(childFragmentManager, KEY_SHOW_ORDERED_PRODUCTS)
            }

        })
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        viewModel.getOrders()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<Order>)
        }
    }
}