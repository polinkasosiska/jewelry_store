package com.sysoliatina.jewelrystore.presentation.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sysoliatina.jewelrystore.databinding.DialogOrderedProductsBinding
import com.sysoliatina.jewelrystore.models.Product
import com.sysoliatina.jewelrystore.presentation.ui.adapter.ProductsForViewAdapter
import kotlin.collections.ArrayList

class OrderedProductsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogOrderedProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductsForViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogOrderedProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        adapter = ProductsForViewAdapter(requireArguments().getParcelableArrayList(ARG_PRODUCTS) ?: arrayListOf()) {

        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter
    }

    companion object {
        private const val ARG_PRODUCTS = "products"

        fun newInstance(products: ArrayList<Product>): OrderedProductsDialogFragment {
            return OrderedProductsDialogFragment().apply {
                arguments = bundleOf(ARG_PRODUCTS to products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}