package com.example.ec3.retrofit

import com.example.ec3.model.CardDetails
import retrofit2.http.GET

interface CardService {
    @GET("api1/games")
    suspend fun  getCoupons() : List<CardDetails>

    }

