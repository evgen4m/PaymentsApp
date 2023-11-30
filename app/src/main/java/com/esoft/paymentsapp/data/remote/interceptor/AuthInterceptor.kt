package com.esoft.paymentsapp.data.remote.interceptor

import com.esoft.paymentsapp.data.local.SettingsDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val settingsDataSource: SettingsDataSource
) : Interceptor {

    private companion object {
        private const val TOKEN = "token"
        private const val APP_KEY = "app-key"
        private const val APP_KEY_VALUE = "12345"
        private const val VERSION = "v"
        private const val VERSION_NAME = "1"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val finalToken: String? = settingsDataSource.getToken()
        request = request.newBuilder()
            .addHeader(APP_KEY, APP_KEY_VALUE)
            .addHeader(VERSION, VERSION_NAME)
            .addHeader(TOKEN, finalToken?: "")
            .build()

        return chain.proceed(request)
    }
}