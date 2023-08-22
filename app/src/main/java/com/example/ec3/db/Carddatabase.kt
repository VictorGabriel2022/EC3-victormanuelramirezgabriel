package com.example.ec3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ec3.model.CardDetails

@Database(entities = [CardDetails::class], version = 1)
abstract class Carddatabase: RoomDatabase() {
    abstract fun cardDao():CardDao

    companion object{
        @Volatile
        private var instance:Carddatabase? = null
        fun getDataBase(context: Context):Carddatabase{
            if (instance == null){
                synchronized(this){
                    instance = buildDataBase(context)
                }
            }
            return  instance!!
        }

        private fun buildDataBase(context: Context): Carddatabase? {
            return Room.databaseBuilder(
                context.applicationContext,
                Carddatabase::class.java,
                "card_database"
            ).fallbackToDestructiveMigration().build()

        }
    }
}