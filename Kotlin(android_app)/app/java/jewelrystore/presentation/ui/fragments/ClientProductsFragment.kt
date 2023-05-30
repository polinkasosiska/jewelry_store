package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentProductsBinding
import com.sysoliatina.jewelrystore.models.Product
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.models.ProductsWithType
import com.sysoliatina.jewelrystore.models.WishList
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ProductTypesHorizontalAdapter
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ProductsAdapter
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.AddCartItemDialogFragment
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.AddProductDialogFragment
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.AddProductTypeDialogFragment
import com.sysoliatina.jewelrystore.presentation.viewmodels.ClientProductsViewModel
import com.sysoliatina.jewelrystore.presentation.viewmodels.ModeratorProductsViewModel


@AndroidEntryPoint
class ClientProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewModel by viewModels<ClientProductsViewModel>()
    private val binding by viewBinding(FragmentProductsBinding::bind)
    private lateinit var adapterProductTypes: ProductTypesHorizontalAdapter
    private lateinit var adapterProducts: ProductsAdapter

    companion object {
        const val KEY_ADD_CART_ITEM = "addCartItem"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterProductTypes = ProductTypesHorizontalAdapter(viewModel.selectedProductTypePosition) { position->
            viewModel.selectedProductTypePosition = position
            adapterProducts.setDataList(adapterProductTypes.getDataList()[position].products as MutableList<Product>)
        }
        binding.rvProductTypes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductTypes.adapter = adapterProductTypes

        adapterProducts = ProductsAdapter(object: ProductsAdapter.OnClickListener {
            override fun onCartClick(productId: Int) {
                AddCartItemDialogFragment
                    .newInstance(productId)
                    .show(childFragmentManager, KEY_ADD_CART_ITEM)
            }

            override fun onLikeClick(productId: Int, isWished: Boolean) {
                if (isWished) {
                    viewModel.deleteWishItem(productId)
                } else {
                    viewModel.addWishItem(productId)
                }
            }

        })
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapterProducts
        binding.btnAdd.hide()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapterProductTypes.setDataList(it as MutableList<ProductsWithType>)
            if (it.isNotEmpty()) {
                adapterProducts.setDataList(it[0].products as MutableList<Product>)
            }
        }
        viewModel.uiStateProduct.subscribeInUI(this, binding.progressBar) {
            showSnackbar(R.string.added_to_cart)
        }
    }
}