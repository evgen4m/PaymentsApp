package com.esoft.paymentsapp.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _screenState = MutableLiveData<LoginState>()
    val screenState: LiveData<LoginState> get() = _screenState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(
                AuthModel(
                    username = username,
                    password = password
                )
            )
            handleLoginResult(result = result)
        }
    }

    private fun handleLoginResult(result: ResponseResult<Boolean>) {
        _screenState.value = LoginState.Loading
        when(result) {
            is ResponseResult.Success -> {
                _screenState.value = LoginState.Success
            }
            is ResponseResult.ServerError -> {
                _screenState.value = LoginState.AuthError(error = result.error)
            }
            is ResponseResult.Error -> {
                _screenState.value = LoginState.Error(error = result.error)
            }
        }
    }

}