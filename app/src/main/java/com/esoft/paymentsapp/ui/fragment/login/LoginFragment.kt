package com.esoft.paymentsapp.ui.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.app.PaymentsApp
import com.esoft.paymentsapp.databinding.FragmentLoginBinding

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<LoginViewModel> {
        viewModelFactory {
            val repository = (requireActivity().application as PaymentsApp).authRepository
            LoginViewModelFactory(
                authRepository = repository
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ::updateUi)
    }

    private fun updateUi(loginState: LoginState) {
        when(loginState) {
            //TODO need impl
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}