package com.example.ec3.retrofit

import com.google.gson.internal.GsonBuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofitinstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.mmobomb.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getCouponService(): CardService = retrofit.create(CardService::class.java)
    }


























