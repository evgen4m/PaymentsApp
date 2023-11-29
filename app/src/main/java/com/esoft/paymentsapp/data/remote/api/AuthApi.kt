package com.esoft.paymentsapp.data.remote.api

import com.esoft.paymentsapp.model.AuthModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body authModel: AuthModel) : ResponseBody

}