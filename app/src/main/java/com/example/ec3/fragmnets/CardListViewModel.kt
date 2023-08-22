package com.example.ec3.fragmnets
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ec3.model.CardDetails
import com.example.ec3.model.getData
import com.example.ec3.retrofit.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardListViewModel:ViewModel() {
    val repository = CardRepository()
    val cardList: MutableLiveData<List<CardDetails>> =MutableLiveData<List<CardDetails>>()

    fun getCouponList(){
        val data = getData()
        cardList.value = data

    }

    fun getCouponsFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCoupons()
            cardList.postValue(response)
        }

}}