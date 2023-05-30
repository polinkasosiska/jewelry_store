package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentWishListBinding
import com.sysoliatina.jewelrystore.models.Product
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.presentation.ui.adapter.WishProductsAdapter
import com.sysoliatina.jewelrystore.presentation.viewmodels.WishListViewModel

@AndroidEntryPoint
class WishListFragment : Fragment(R.layout.fragment_wish_list) {

    private val viewModel by viewModels<WishListViewModel>()
    private val binding by viewBinding(FragmentWishListBinding::bind)
    private lateinit var adapter: WishProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WishProductsAdapter {

        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
        viewModel.getWishList()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            adapter.setDataList(it as MutableList<Product>)
        }
    }
}