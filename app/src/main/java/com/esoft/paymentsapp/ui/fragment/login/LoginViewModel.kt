package com.esoft.paymentsapp.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.repository.AuthRepository
import com.esoft.paymentsapp.ui.base.LiveEvent
import com.esoft.paymentsapp.ui.base.MutableLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _screenState = MutableLiveData<LoginState>()
    val screenState: LiveData<LoginState> get() = _screenState

    private val _passwordErrorEvent = MutableLiveEvent()
    val passwordErrorEvent: LiveEvent = _passwordErrorEvent

    private val _userNameErrorEvent = MutableLiveEvent()
    val userNameErrorEvent: LiveEvent = _userNameErrorEvent

    private val _dataFieldsEmpty = MutableLiveEvent()
    val dataFieldsEmpty: LiveEvent = _dataFieldsEmpty

    private val _userIsAuthorized = MutableLiveData(false)
    val userIsAuthorized: LiveData<Boolean> = _userIsAuthorized

    private val _loginAvailable = MutableLiveEvent()
    val loginAvailable: LiveEvent = _loginAvailable

    fun login(username: String, password: String) {
        _screenState.value = LoginState.Loading(loading = true)
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

    fun updateLoginData(username: String, password: String) {
        handleLoginData(name = username, password = password)
    }

    private fun handleLoginResult(result: ResponseResult<Nothing>) {
        _screenState.value = LoginState.Loading(loading = false)
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

    private fun handleLoginData(name: String, password: String) {
        when {
            !userNameIsNotEmpty(name = name) && isPasswordValid(password = password) -> {
                _userNameErrorEvent()
            }
            !isPasswordValid(password = password) && userNameIsNotEmpty(name = name)-> {
                _passwordErrorEvent()
            }
            !isPasswordValid(password = password) && !userNameIsNotEmpty(name = name) -> {
                _dataFieldsEmpty()
            }
            else -> _loginAvailable()
        }
    }

    private fun userNameIsNotEmpty(name: String) : Boolean =
        name.isNotBlank()

    private fun isPasswordValid(password: String): Boolean =
        password.length >= 5

    fun checkUserIsAuthorized() {
        _userIsAuthorized.value = authRepository.checkUserIsAuthorized()
    }


}