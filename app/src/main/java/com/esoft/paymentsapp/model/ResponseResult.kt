package com.esoft.paymentsapp.model


sealed class ResponseResult<out T> {

    data class Success<out T>(val data: T) : ResponseResult<T>()

    data class ServerError(val error: String) : ResponseResult<Nothing>()

    data class Error(val error: String) : ResponseResult<Nothing>()

}

