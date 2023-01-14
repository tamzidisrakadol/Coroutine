package com.example.androidcourutine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val Tag:String ="KotlinFun"

    init {
        viewModelScope.launch {
            delay(2000L)
            for (i in 1..25){
                Log.d(Tag,i.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(Tag,"ViewModel Destroyed")
    }
}