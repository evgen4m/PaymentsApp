package com.esoft.paymentsapp.data.local

import android.content.Context
import android.content.SharedPreferences

interface SettingsDataSource {

    fun saveToken(token: String)

    fun getToken() : String?

    fun clearToken()

}

class SettingsDataSourceImpl(private val context: Context) : SettingsDataSource {

    private companion object {
        const val STORAGE_NAME = "SettingsStorage"
        const val TOKEN_KEY = "tokenKey"
    }

    private var settings: SharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor? = settings.edit()

    override fun saveToken(token: String) {
        editor?.putString(TOKEN_KEY, token)
        editor?.commit()
    }

    override fun getToken(): String? = settings.getString(TOKEN_KEY, null)

    override fun clearToken() {
        editor?.remove(TOKEN_KEY)
        editor?.commit()
    }
}