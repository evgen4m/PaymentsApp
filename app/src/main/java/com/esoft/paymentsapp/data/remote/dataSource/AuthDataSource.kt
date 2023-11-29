package com.esoft.paymentsapp.data.remote.dataSource

import com.esoft.paymentsapp.data.remote.api.AuthApi
import com.esoft.paymentsapp.model.AuthModel
import com.esoft.paymentsapp.model.AuthResponse
import com.esoft.paymentsapp.model.SuccessResponse
import org.json.JSONObject

interface AuthDataSource {

    suspend fun login(authModel: AuthModel) : AuthResponse

}

class AuthDataSourceImpl(
    private val authApi: AuthApi
) : AuthDataSource {

    companion object {
        private const val SUCCESS = "success"
        private const val TOKEN = "token"
        private const val ERROR = "error"
        private const val ERROR_CODE = "error_code"
        private const val ERROR_MSG = "error_msg"
        private const val RESPONSE = "response"
    }

    override suspend fun login(authModel: AuthModel): AuthResponse {
        return getObjectFromJson(authApi.login(authModel = authModel).string())
    }

    private fun getObjectFromJson(jsonString: String): AuthResponse {
        val jsonContact = JSONObject(jsonString)
        val success: Boolean = jsonContact.getBoolean(SUCCESS)
        if (success) {
            val jsonToken = jsonContact.getJSONObject(RESPONSE)
            return AuthResponse(
                success = success,
                response = SuccessResponse.Token(
                    token = jsonToken.getString(TOKEN)
                )
            )
        } else {
            val jsonError = jsonContact.getJSONObject(ERROR)
            return AuthResponse(
                success = success,
                response = SuccessResponse.ErrorModel(
                    code = jsonError.getInt(ERROR_CODE),
                    message = jsonError.getString(ERROR_MSG)
                )
            )
        }
    }

}