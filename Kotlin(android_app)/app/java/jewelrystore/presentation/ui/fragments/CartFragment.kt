package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentCartListBinding
import com.sysoliatina.jewelrystore.databinding.FragmentWishListBinding
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.presentation.ui.adapter.CartProductsAdapter
import com.sysoliatina.jewelrystore.presentation.ui.adapter.DeletableProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.presentation.viewmodels.CartViewModel


@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart_list) {

    private val viewModel by viewModels<CartViewModel>()
    private val binding by viewBinding(FragmentCartListBinding::bind)
    private lateinit var adapter: CartProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartProductsAdapter {
            confirmAction(R.string.delete_request) { _, _ ->
                deleteCartItem(it)
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        viewModel.getCartList()
        binding.btnMakeOrder.setOnClickListener {
            confirmAction(R.string.add_order_request) { _, _ ->
                viewModel.addOrder()
            }
        }
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<Product>)
        }
    }

    private fun deleteCartItem(productTypeId: Int) {
        viewModel.deleteCartItem(productTypeId).subscribeInUI(this, binding.progressBar) {
            adapter.deleteItem(it.productId)
        }
    }
}