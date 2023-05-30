package com.sysoliatina.jewelrystore.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.sysoliatina.jewelrystore.R
import com.sysoliatina.jewelrystore.common.*
import com.sysoliatina.jewelrystore.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import com.sysoliatina.jewelrystore.domain.login.LoginState
import com.sysoliatina.jewelrystore.presentation.ui.activity.ClientActivity
import com.sysoliatina.jewelrystore.presentation.ui.activity.MainActivity
import com.sysoliatina.jewelrystore.presentation.ui.activity.ModeratorActivity
import com.sysoliatina.jewelrystore.presentation.viewmodels.LoginViewModel


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            loginLayout.editText?.doOnTextChanged { _, _, _, _ ->
                loginLayout.error = null
            }
            passwordLayout.editText?.doOnTextChanged { _, _, _, _ ->
                passwordLayout.error = null
            }
            btnSignUp.setOnClickListener {
                val bundle = Bundle()
                Navigation.findNavController(root)
                    .navigate(R.id.action_loginFragment_to_signUpFragment, bundle)
            }
            btnLogin.setOnClickListener() {
                auth(login.text.trimString(), password.text.trimString())
            }
        }
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.uiState.onEach {
            binding.progressBar.showIf(it is LoginState.Loading)
            if (it is LoginState.Unauthorized) {
                binding.progressBar.showIf(false)
                binding.main.showIf(true)
            } else if (it is LoginState.Success) {
                showSnackbar(it.data.fullName)
                requireActivity().finish()
                if (it.data.isModerator) {
                    startActivity(Intent(requireContext(), ModeratorActivity::class.java))
                } else {
                    startActivity(Intent(requireContext(), ClientActivity::class.java))
                }
            } else if (it is LoginState.Error) {
                showSnackbar(it.exception)
            } else if (it is LoginState.EmailError) {
                binding.loginLayout.error = getString(it.messageResId)
            } else if (it is LoginState.PasswordError) {
                binding.passwordLayout.error = getString(it.messageResId)
            }
        }.launchWhenStartedCollect(lifecycleScope)
    }

    private fun auth(email: String, password: String) {
        viewModel.auth(email, password)
    }
}