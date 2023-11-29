package com.esoft.paymentsapp.repository

import com.esoft.paymentsapp.data.local.SettingsDataSource
import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSource
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.ResponseResult
import com.esoft.paymentsapp.model.SuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

interface AuthRepository {

    suspend fun login(authModel: AuthModel) : ResponseResult

    fun checkUserIsAuthorized(): Boolean

}

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val settingsDataSource: SettingsDataSource
) : AuthRepository {

    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }

    override suspend fun login(authModel: AuthModel): ResponseResult = withContext(Dispatchers.IO) {
        supervisorScope {
            try {
                val result = authDataSource.login(authModel = authModel)
                if (result.success) {
                    val token = (result.response as SuccessResponse.Token)
                    settingsDataSource.saveToken(token = token.token)
                    ResponseResult.Success
                } else {
                    val errorMessage = (result.response as SuccessResponse.ErrorModel)
                    ResponseResult.ServerError(error = errorMessage.message)
                }
            } catch (e: Exception) {
                ResponseResult.Error(error = UNKNOWN_ERROR)
            }
        }
    }

    override fun checkUserIsAuthorized(): Boolean {
        return settingsDataSource.getToken() != null
    }


}