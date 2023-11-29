package com.esoft.paymentsapp.model

data class AuthResponse(
    val success: Boolean,
    val response: SuccessResponse
)

sealed class SuccessResponse {

    data class Token(val token: String) : SuccessResponse()

    data class ErrorModel(
        val code: Int,
        val message: String
    ) : SuccessResponse()

}


