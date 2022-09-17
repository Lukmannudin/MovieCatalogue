package com.lukmannudin.moviecatalogue.di

import com.lukmannudin.moviecatalogue.BuildConfig
import com.lukmannudin.moviecatalogue.api.ApiHelper
import com.lukmannudin.moviecatalogue.api.ApiHelperImpl
import com.lukmannudin.moviecatalogue.api.ApiService
import com.lukmannudin.moviecatalogue.utils.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Lukmannudin on 09/05/21.
 */


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val apiKeyInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url.newBuilder().addQueryParameter(
                    "api_key", Keys.getApiKey()).build()
                val request = original.newBuilder().url(originalHttpUrl).build()
                return chain.proceed(request)
            }
        }

       return if (BuildConfig.DEBUG){
            val logginInterceptor = HttpLoggingInterceptor()

            logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(logginInterceptor)
                .addInterceptor(apiKeyInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Keys.getBaseUrl())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}