package com.esoft.paymentsapp.model

open class BaseResponse

data class AuthResponse<T: BaseResponse>(
    val success: Boolean,
    val response: T
)


