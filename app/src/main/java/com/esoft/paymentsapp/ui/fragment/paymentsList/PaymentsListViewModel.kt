package com.esoft.paymentsapp.ui.fragment.paymentsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esoft.paymentsapp.model.PaymentItem
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.repository.AuthRepository
import com.esoft.paymentsapp.repository.PaymentsRepository
import com.esoft.paymentsapp.ui.base.LiveEvent
import com.esoft.paymentsapp.ui.base.MutableLiveEvent
import com.esoft.paymentsapp.ui.fragment.login.LoginState
import kotlinx.coroutines.launch

class PaymentsListViewModel(
    private val paymentsRepository: PaymentsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<PaymentsState>()
    val screenState: LiveData<PaymentsState> get() = _screenState

    init {
        loadPaymentsItems()
    }

    fun loadPaymentsItems() {
        _screenState.value = PaymentsState.Loading(loading = true)
        viewModelScope.launch {
            val result = paymentsRepository.getPaymentsList()
            handleResult(result = result)
        }
    }

    private fun handleResult(result: ResponseResult<List<PaymentItem>>) {
        _screenState.value = PaymentsState.Loading(loading = false)
        when(result) {
            is ResponseResult.Success -> {
                _screenState.value = PaymentsState.Success(data = result.data?: emptyList())
            }
            is ResponseResult.Error -> {
                _screenState.value = PaymentsState.Error(error = result.error)
            }

            is ResponseResult.ServerError -> {
                _screenState.value = PaymentsState.Error(error = result.error)
            }
        }
    }

    fun logout() {
        _screenState.value = PaymentsState.ExitScreen
        authRepository.logout()
    }

}