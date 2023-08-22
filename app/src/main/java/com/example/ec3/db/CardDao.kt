package com.example.ec3.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ec3.model.CardDetails

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getFavorites(): List<CardDetails>
    @Query("DELETE FROM card WHERE id = :cardId")
    suspend fun removeFavorite(cardId: Int)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(cardDetails: CardDetails)
}