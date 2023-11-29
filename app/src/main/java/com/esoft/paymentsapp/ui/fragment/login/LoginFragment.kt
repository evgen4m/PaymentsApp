package com.esoft.paymentsapp.ui.fragment.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.esoft.paymentsapp.R
import com.esoft.paymentsapp.app.PaymentsApp
import com.esoft.paymentsapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModels<LoginViewModel> {
        val repository = (requireActivity().application as PaymentsApp).authRepository
        LoginViewModelFactory(
            authRepository = repository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        observeViewModel()
        initViews()
        viewModel.checkUserIsAuthorized()
    }

    private fun initViews() = with(binding) {
        buttonLogin.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() = with(binding) {
        viewModel.updateLoginData(
            username = textFirstnameInput.text.toString(),
            password = textPassInput.text.toString()
        )
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner, ::updateUi)
        viewModel.userNameErrorEvent.observe(viewLifecycleOwner) { showUserNameError() }
        viewModel.passwordErrorEvent.observe(viewLifecycleOwner) { showPasswordError() }
        viewModel.dataFieldsEmpty.observe(viewLifecycleOwner) { showEmptyFieldsError() }
        viewModel.userIsAuthorized.observe(viewLifecycleOwner) { isAuthorized ->
            if (isAuthorized) navigateToPaymentsListScreen()
        }
        viewModel.loginAvailable.observe(viewLifecycleOwner) { login() }
    }

    private fun showEmptyFieldsError() {
        showErrorMessage(message = getString(R.string.empty_login_fileds))
    }

    private fun login() = with(binding) {
        viewModel.login(
            username = textFirstnameInput.text.toString(),
            password = textPassInput.text.toString()
        )
    }

    private fun showPasswordError() {
        binding.textPassInput.error = getString(R.string.password_is_short)
    }

    private fun showUserNameError() {
        binding.textFirstnameInput.error = getString(R.string.enter_user_name)
    }

    private fun updateUi(loginState: LoginState) {
        when(loginState) {
            is LoginState.Success -> navigateToPaymentsListScreen()
            is LoginState.Error -> showErrorMessage(message = loginState.error)
            is LoginState.AuthError -> showErrorMessage(message = loginState.error)
            is LoginState.Loading -> showLoading(show = loginState.loading)
        }
    }

    private fun showLoading(show: Boolean) = with(binding) {
        childLoginView.visibility = if (show) View.GONE else View.VISIBLE
        loadingView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToPaymentsListScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_paymentsListFragment, null, navOptions {
            popUpTo(R.id.loginFragment) {
                inclusive = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}