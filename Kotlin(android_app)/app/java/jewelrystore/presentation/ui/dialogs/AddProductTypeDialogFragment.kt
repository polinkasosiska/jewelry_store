package com.sysoliatina.jewelrystore.presentation.ui.dialogs

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sysoliatina.jewelrystore.common.trimString
import com.sysoliatina.jewelrystore.databinding.DialogAddProductTypeBinding
import com.sysoliatina.jewelrystore.presentation.viewmodels.ProductTypesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductTypeDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogAddProductTypeBinding? = null
    private val binding get() = _binding!!
    val viewModel: ProductTypesViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddProductTypeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        with(binding) {
            btnSave.setOnClickListener {
                viewModel.addProductType(inputProductTypeName.text.trimString())
                dismiss()
            }
        }
    }

    companion object {

        fun newInstance(): AddProductTypeDialogFragment {
            return AddProductTypeDialogFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}