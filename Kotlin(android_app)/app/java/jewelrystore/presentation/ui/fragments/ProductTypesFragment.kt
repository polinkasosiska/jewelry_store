package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentProductTypesBinding
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.models.ProductType
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ProductTypesAdapter
import com.sysoliatina.jewelrystore.presentation.ui.dialogs.AddProductTypeDialogFragment
import com.sysoliatina.jewelrystore.presentation.viewmodels.ProductTypesViewModel


@AndroidEntryPoint
class ProductTypesFragment : Fragment(R.layout.fragment_product_types) {

    private val viewModel by viewModels<ProductTypesViewModel>()
    private val binding by viewBinding(FragmentProductTypesBinding::bind)
    private lateinit var adapter: ProductTypesAdapter

    companion object {
        const val KEY_ADD_PRODUCT_TYPE = "addProductType"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductTypesAdapter { id->
            confirmAction(R.string.delete_request) { _, _ ->
                deleteProductType(id)
            }
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        binding.btnAdd.setOnClickListener {
            AddProductTypeDialogFragment
                .newInstance()
                .show(childFragmentManager, KEY_ADD_PRODUCT_TYPE)
        }
        subscribeObserver()
    }

    private fun deleteProductType(productTypeId: Int) {
        viewModel.deleteProductType(productTypeId).subscribeInUI(this, binding.progressBar) {
            adapter.deleteItem(it)
        }
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<ProductType>)
        }
    }
}