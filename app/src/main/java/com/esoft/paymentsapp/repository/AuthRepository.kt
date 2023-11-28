package com.esoft.paymentsapp.repository

import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSource
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.BaseResponse
import com.esoft.paymentsapp.model.ErrorModel
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.model.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

interface AuthRepository {

    suspend fun login(authModel: AuthModel) : ResponseResult<Boolean>

}

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }

    override suspend fun login(authModel: AuthModel): ResponseResult<Boolean> = withContext(Dispatchers.IO) {
        supervisorScope {
            try {
                val result = authDataSource.login(authModel = authModel)
                if (result.success) {
                    val token = (result.response as Token)
                    //settingsDataSource.saveToken(token = token)
                    ResponseResult.Success(data = result.success)
                } else {
                    val errorMessage = (result.response as ErrorModel)
                    ResponseResult.ServerError(error = errorMessage.message)
                }
            } catch (e: Exception) {
                ResponseResult.Error(error = e.localizedMessage ?: UNKNOWN_ERROR)
            }
        }
    }

}