package com.example.ec3.fragmnets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ec3.db.Carddatabase
import com.example.ec3.model.CardDetails
import com.example.ec3.retrofit.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardDetailViewModel (application: Application): AndroidViewModel(application){
    private val repository: CardRepository
    init {
        val db = Carddatabase.getDataBase(application)
        repository = CardRepository(db.cardDao())
    }
    fun addFavorite(cardDetails:CardDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(cardDetails)
        }
    }
    fun removeFavorite(cardId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFavorite(cardId)
        }
    }

}