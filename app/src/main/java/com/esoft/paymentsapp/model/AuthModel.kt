package com.esoft.paymentsapp.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
    @SerializedName("login")
    val username: String,

    @SerializedName("password")
    val password: String
)
