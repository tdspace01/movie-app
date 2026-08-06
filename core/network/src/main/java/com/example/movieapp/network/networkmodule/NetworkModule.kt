package com.example.movieapp.network.networkmodule

import com.example.movieapp.domain.repository.network.NetworkObserver
import com.example.movieapp.network.BuildConfig
import com.example.movieapp.network.interceptor.AuthInterceptor
import com.example.movieapp.network.networkobserver.NetworkObserverImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single<NetworkObserver> {
        NetworkObserverImpl(context = androidContext())
    }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val interceptor = AuthInterceptor(bearerToken = BuildConfig.BEARER_TOKEN)

        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(
                get<Json>()
                    .asConverterFactory("application/json".toMediaType())
            )
            .build()
    }
}