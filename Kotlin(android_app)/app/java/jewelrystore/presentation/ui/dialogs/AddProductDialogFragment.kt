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
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.sysoliatina.jewelrystore.common.formatToString
import com.sysoliatina.jewelrystore.common.trimString
import com.sysoliatina.jewelrystore.databinding.DialogAddProductBinding
import com.sysoliatina.jewelrystore.databinding.DialogAddProductTypeBinding
import com.sysoliatina.jewelrystore.presentation.viewmodels.ModeratorProductsViewModel
import com.sysoliatina.jewelrystore.presentation.viewmodels.ProductTypesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddProductDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogAddProductBinding? = null
    private val binding get() = _binding!!
    val viewModel: ModeratorProductsViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddProductBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        with(binding) {
            initDateInputLayout(dateInputLayout)
            btnSave.setOnClickListener {
                viewModel.addProduct(
                    requireArguments().getInt(ARG_PRODUCT_TYPE_ID),
                    inputProductName.text.trimString(),
                    inputPrice.text.trimString().toInt(),
                    requireArguments().getString(ARG_MANUFACTURE_DATE) ?: Date().formatToString("yyyy-MM-dd")!!,
                    inputMaterial.text.trimString(),
                    inputColor.text.trimString(),
                    inputCountry.text.trimString(),
                    inputWeight.text.trimString().toInt(),
                )
                dismiss()
            }
        }
    }

    private fun initDateInputLayout(textInputLayout: TextInputLayout) {
        textInputLayout.editText?.keyListener = null
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .build()
        val onClickListener = View.OnClickListener() {
            datePicker.show(parentFragmentManager, "datePicker")
        }
        textInputLayout.setOnClickListener(onClickListener)
        textInputLayout.editText?.setOnClickListener(onClickListener)
        datePicker.addOnPositiveButtonClickListener {
            datePicker.selection?.let { date ->
                val dateStr = Date(date).formatToString("yyyy-MM-dd")
                textInputLayout.editText?.setText(dateStr)
                this.apply {
                    arguments = bundleOf(ARG_PRODUCT_TYPE_ID to requireArguments().getInt(ARG_PRODUCT_TYPE_ID), ARG_MANUFACTURE_DATE to dateStr)
                }
            }
        }
    }

    companion object {
        private const val ARG_PRODUCT_TYPE_ID = "productTypeId"
        private const val ARG_MANUFACTURE_DATE = "manufactureDate"

        fun newInstance(productTypeId: Int): AddProductDialogFragment {
            return AddProductDialogFragment().apply {
                arguments = bundleOf(ARG_PRODUCT_TYPE_ID to productTypeId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}