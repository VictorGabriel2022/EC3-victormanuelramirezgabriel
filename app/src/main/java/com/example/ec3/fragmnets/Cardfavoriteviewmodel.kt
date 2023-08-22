package com.example.ec3.fragmnets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ec3.db.Carddatabase
import com.example.ec3.model.CardDetails
import com.example.ec3.retrofit.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Cardfavoriteviewmodel (application: Application): AndroidViewModel(application) {
    private val repository: CardRepository
    private val _favorites: MutableLiveData<List<CardDetails>> = MutableLiveData()
    val favorites: LiveData<List<CardDetails>> = _favorites

    init {
        val db = Carddatabase.getDataBase(application)
        repository = CardRepository(db.cardDao())
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getFavorites()
            _favorites.postValue(data)
        }
    }
}