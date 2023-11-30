package com.esoft.paymentsapp.ui.fragment.paymentsList

import com.esoft.paymentsapp.model.PaymentItem

sealed class PaymentsState {

    data class Success(val data: List<PaymentItem>) : PaymentsState()

    data class Error(val error: String) : PaymentsState()

    data class Loading(val loading: Boolean) : PaymentsState()

    data object ExitScreen: PaymentsState()

}