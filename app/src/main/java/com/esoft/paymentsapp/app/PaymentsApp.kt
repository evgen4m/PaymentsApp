package com.esoft.paymentsapp.app

import android.app.Application
import com.esoft.paymentsapp.data.remote.api.AuthApi
import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSource
import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSourceImpl
import com.esoft.paymentsapp.repository.AuthRepository
import com.esoft.paymentsapp.repository.AuthRepositoryImpl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentsApp: Application() {

    companion object {
        private const val BASE_URL = "https://easypay.world/api-test/"
    }

    private lateinit var retrofit: Retrofit

    private lateinit var authDataSource: AuthDataSource
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        authDataSource = AuthDataSourceImpl(authApi = retrofit.create(AuthApi::class.java))
        authRepository = AuthRepositoryImpl(authDataSource = authDataSource)

    }

}