package com.esoft.paymentsapp.data.remote.api

import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.BaseResponse
import retrofit2.http.GET

interface PaymentsApi {

    @GET("/payments")
    suspend fun getPaymentList() : AuthResponse<BaseResponse>

}