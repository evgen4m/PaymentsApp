package com.esoft.paymentsapp.repository

import com.esoft.paymentsapp.data.remote.dataSource.PaymentsDataSource
import com.esoft.paymentsapp.model.PaymentItem
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.model.SuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

interface PaymentsRepository {

    suspend fun getPaymentsList() : ResponseResult<List<PaymentItem>>

}

class PaymentsRepositoryImpl(
    private val paymentsDataSource: PaymentsDataSource
) : PaymentsRepository {

    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }

    override suspend fun getPaymentsList(): ResponseResult<List<PaymentItem>> = withContext(Dispatchers.IO) {
        supervisorScope {
            try {
                val result = paymentsDataSource.getPaymentsList()
                if (result.success) {
                    val items = (result.response as SuccessResponse.PaymentsItems)
                    ResponseResult.Success(data = items.items)
                } else {
                    val errorMessage = (result.response as SuccessResponse.ErrorModel)
                    ResponseResult.ServerError(error = errorMessage.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ResponseResult.Error(error = UNKNOWN_ERROR)
            }
        }
    }

}