package com.example.offlinecaching

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.offlinecaching.network.NetworkQuake
import com.example.offlinecaching.network.UsgsApi
import kotlinx.coroutines.launch
import java.lang.Exception

class UsgsViewModel : ViewModel() {
    private val _quakes = MutableLiveData<NetworkQuake>()
    val quakes: LiveData<NetworkQuake>
        get() = _quakes

    init {
        getQuakes()
    }

    private fun getQuakes() {
        viewModelScope.launch {
            try {
                _quakes.value = UsgsApi.retrofitService.getQuakes()
            } catch (e: Exception) {
                _quakes.value = NetworkQuake(listOf())
                Log.d("UsgsViewModel", "Error: ${e.message}")
            }
        }
    }
}