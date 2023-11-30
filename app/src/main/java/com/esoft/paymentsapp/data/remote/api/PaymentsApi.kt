package com.esoft.paymentsapp.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface PaymentsApi {

    @GET("payments")
    suspend fun getPaymentList() : ResponseBody

}