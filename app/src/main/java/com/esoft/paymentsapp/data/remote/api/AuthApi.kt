package com.esoft.paymentsapp.data.remote.api

import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun login(@Body authModel: AuthModel) : AuthResponse<BaseResponse>

}