package com.esoft.paymentsapp.ui.fragment.login

sealed class LoginState {

    data object Success : LoginState()

    data object Loading : LoginState()

    data class Error(val error: String) : LoginState()

    data class AuthError(val error: String) : LoginState()

}
