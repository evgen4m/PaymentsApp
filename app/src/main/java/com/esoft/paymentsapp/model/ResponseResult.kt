package com.esoft.paymentsapp.model


sealed class ResponseResult {

    data object Success: ResponseResult()

    data class ServerError(val error: String) : ResponseResult()

    data class Error(val error: String) : ResponseResult()

}

