package com.esoft.paymentsapp.ui.fragment.paymentsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esoft.paymentsapp.repository.AuthRepository
import com.esoft.paymentsapp.repository.PaymentsRepository

class PaymentsListViewModelFactory(
    private val paymentsRepository: PaymentsRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
         PaymentsListViewModel(
             paymentsRepository = paymentsRepository,
             authRepository = authRepository
         ) as T


}