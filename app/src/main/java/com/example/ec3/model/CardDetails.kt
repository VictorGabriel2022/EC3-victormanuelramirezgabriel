package com.example.ec3.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "card")
@Parcelize
data class CardDetails(
    @PrimaryKey
    val id:Int,
    val title:String,
    val thumbnail: String,
    val short_description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val profile_url: String,
    var isFavorite:Boolean=false
) : Parcelable
fun getData():List<CardDetails>{
    return listOf(
        CardDetails
            (1,"dfsdfsdf","ruta_imagen"   ,
            "Big changes come to the Overwatch formula in this sequel...and so does PvE content, eventually.",
            "https://www.mmobomb.com/open/overwatch-2",
            "Shooter",
            "PC (Windows)",
            "Activision Blizzard King",
            "Blizzard Entertainment",
            "2022-10-04",
            "https://www.mmobomb.com/overwatch-2"),
    )
}
