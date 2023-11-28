package com.esoft.paymentsapp.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esoft.paymentsapp.repository.AuthRepository

class LoginViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
         LoginViewModel(authRepository = authRepository) as T


}