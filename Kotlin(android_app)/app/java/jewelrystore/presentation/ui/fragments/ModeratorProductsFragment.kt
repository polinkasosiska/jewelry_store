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
import com.sysoliatina.jewelrystore.presentation.ui.adapter.DeletableProductsAdapter
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ProductTypesHorizontalAdapter
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.AddProductDialogFragment
import com.sysoliatina.jewelrystore.presentation.viewmodels.ModeratorProductsViewModel


@AndroidEntryPoint
class ModeratorProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewModel by viewModels<ModeratorProductsViewModel>()
    private val binding by viewBinding(FragmentProductsBinding::bind)
    private lateinit var adapterProductTypes: ProductTypesHorizontalAdapter
    private lateinit var adapterProducts: DeletableProductsAdapter

    companion object {
        const val KEY_ADD_PRODUCT_TYPE = "addProductType"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterProductTypes = ProductTypesHorizontalAdapter(viewModel.selectedProductTypePosition) { position->
            viewModel.selectedProductTypePosition = position
            adapterProducts.setDataList(adapterProductTypes.getDataList()[position].products as MutableList<Product>)
        }
        binding.rvProductTypes.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProductTypes.adapter = adapterProductTypes

        adapterProducts = DeletableProductsAdapter { id->
            confirmAction(R.string.delete_request) { _, _ ->
                deleteProduct(id)
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapterProducts
        binding.btnAdd.setOnClickListener {
            AddProductDialogFragment
                .newInstance(adapterProductTypes.getDataList()[viewModel.selectedProductTypePosition].productTypeId)
                .show(childFragmentManager, KEY_ADD_PRODUCT_TYPE)
        }
        subscribeObserver()
    }


    private fun deleteProduct(productTypeId: Int) {
        viewModel.deleteProduct(productTypeId).subscribeInUI(this, binding.progressBar) {
            adapterProducts.deleteItem(it)
        }
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapterProductTypes.setDataList(it as MutableList<ProductsWithType>)
            adapterProducts.setDataList(it[0].products as MutableList<Product>)
        }
    }
}