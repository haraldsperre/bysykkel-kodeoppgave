package com.example.cyclebikes.model.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://gbfs.urbansharing.com/oslobysykkel.no/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpClient = OkHttpClient.Builder()
    .retryOnConnectionFailure(true)
    .connectTimeout(10, TimeUnit.SECONDS)

private val retrofitService = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient.build())
    .build()

object SanntidApi {
    val retrofit : SanntidService by lazy {
        retrofitService.create(SanntidService::class.java)
    }
}