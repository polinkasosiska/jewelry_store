package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentSignUpBinding
import com.sysoliatina.jewelrystore.presentation.ui.activity.LaunchActivity
import com.sysoliatina.jewelrystore.presentation.ui.activity.LaunchActivity_GeneratedInjector
import com.sysoliatina.jewelrystore.presentation.ui.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import com.sysoliatina.jewelrystore.presentation.viewmodels.SignUpViewModel

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val viewModel by viewModels<SignUpViewModel>()
    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSignUp.setOnClickListener {
                if (!(nameLayout.isNotEmpty() && loginLayout.isValidEmail() && passwordLayout.isNotEmpty())) {
                    return@setOnClickListener
                }
                viewModel.signUp(
                    fullName = name.text.trimString(), address = address.text.trimString(),
                    phone = phone.text.trimString(), email = email.text.trimString(),
                    password = password.text.trimString()
                )
            }
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.uiState.subscribeInUI(this, binding.progressBar) {
            requireActivity().finish()
            startActivity(Intent(requireContext(), LaunchActivity::class.java))
        }
    }
}