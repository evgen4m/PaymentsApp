package com.esoft.paymentsapp.data.remote.dataSource

import com.esoft.paymentsapp.data.remote.api.AuthApi
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.BaseResponse

interface AuthDataSource {

    suspend fun login(authModel: AuthModel) : AuthResponse<BaseResponse>

}

class AuthDataSourceImpl(
    private val authApi: AuthApi
) : AuthDataSource {

    override suspend fun login(authModel: AuthModel): AuthResponse<BaseResponse> {
        return authApi.login(authModel = authModel)
    }

}