package com.example.ec3.retrofit

import com.example.ec3.db.CardDao
import com.example.ec3.model.CardDetails

class CardRepository(val  cardDao : CardDao? = null) {

    suspend fun getCoupons(): List<CardDetails>{
        return  try {
            val response = Retrofitinstance.getCouponService().getCoupons()
            response
        }catch (e:Exception){
            emptyList()
        }
    }
    suspend fun addFavorite(coupon: CardDetails){
        cardDao?.let {
            it.addFavorite(coupon)
        }
    }

    fun getFavorites():List<CardDetails>{
        cardDao?.let {
            return it.getFavorites()
        } ?: kotlin.run {
            return listOf()
        }
    }
    suspend fun removeFavorite(cardId: Int) {
        cardDao?.let {
            it.removeFavorite(cardId)
        }
    }
}