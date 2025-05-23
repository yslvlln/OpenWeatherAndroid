package com.yslvlln.core.network.di

import androidx.tracing.trace
import com.google.firebase.auth.FirebaseAuth
import com.yslvlln.core.common.BASE_URL
import com.yslvlln.core.common.DATA
import com.yslvlln.core.common.ENDPOINT_FORMAT
import com.yslvlln.core.common.ENDPOINT_VERSION
import com.yslvlln.core.network.BuildConfig
import com.yslvlln.core.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = trace("OpenWeatherOkHttp") {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    },
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        json: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>
    ): Retrofit = trace("OpenWeatherRetrofit") {
        Retrofit.Builder()
            .baseUrl(
                String.format(
                    ENDPOINT_FORMAT,
                    BASE_URL,
                    DATA,
                    ENDPOINT_VERSION
                )
            )
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = trace("OpenWeatherFirebaseAuth") {
        FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesAuthApiService(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)
}