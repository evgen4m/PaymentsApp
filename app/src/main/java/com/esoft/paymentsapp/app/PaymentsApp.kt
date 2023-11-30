package com.esoft.paymentsapp.app

import android.app.Application
import com.esoft.paymentsapp.data.local.SettingsDataSource
import com.esoft.paymentsapp.data.local.SettingsDataSourceImpl
import com.esoft.paymentsapp.data.remote.api.AuthApi
import com.esoft.paymentsapp.data.remote.api.PaymentsApi
import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSource
import com.esoft.paymentsapp.data.remote.dataSource.AuthDataSourceImpl
import com.esoft.paymentsapp.data.remote.dataSource.PaymentsDataSource
import com.esoft.paymentsapp.data.remote.dataSource.PaymentsDataSourceImpl
import com.esoft.paymentsapp.data.remote.interceptor.AuthInterceptor
import com.esoft.paymentsapp.repository.AuthRepository
import com.esoft.paymentsapp.repository.AuthRepositoryImpl
import com.esoft.paymentsapp.repository.PaymentsRepository
import com.esoft.paymentsapp.repository.PaymentsRepositoryImpl
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentsApp: Application() {

    companion object {
        private const val BASE_URL = "https://easypay.world/api-test/"
    }

    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var authInterceptor: AuthInterceptor

    private lateinit var authDataSource: AuthDataSource
    private lateinit var settingsDataSource: SettingsDataSource
    private lateinit var paymentsDataSource: PaymentsDataSource

    lateinit var authRepository: AuthRepository
    lateinit var paymentsRepository: PaymentsRepository

    override fun onCreate() {
        super.onCreate()

        settingsDataSource = SettingsDataSourceImpl(context = this)
        authInterceptor = AuthInterceptor(settingsDataSource = settingsDataSource)

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(settingsDataSource))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()

        authDataSource = AuthDataSourceImpl(authApi = retrofit.create(AuthApi::class.java))
        paymentsDataSource = PaymentsDataSourceImpl(paymentsApi = retrofit.create(PaymentsApi::class.java))

        authRepository = AuthRepositoryImpl(
            authDataSource = authDataSource,
            settingsDataSource = settingsDataSource
        )

        paymentsRepository = PaymentsRepositoryImpl(
            paymentsDataSource = paymentsDataSource
        )

    }

}