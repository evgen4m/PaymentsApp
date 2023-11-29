package com.esoft.paymentsapp.data.remote.api

import com.esoft.paymentsapp.model.AuthResponse
import retrofit2.http.GET

interface PaymentsApi {

    @GET("/payments")
    suspend fun getPaymentList() : AuthResponse

}