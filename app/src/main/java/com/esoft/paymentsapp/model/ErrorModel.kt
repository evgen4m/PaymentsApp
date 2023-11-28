package com.esoft.paymentsapp.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error_code")
    val code: Int,

    @SerializedName("error_msg")
    val message: String
) : BaseResponse()