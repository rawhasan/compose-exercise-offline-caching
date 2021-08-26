package com.example.offlinecaching.viewmodel

import androidx.lifecycle.*
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.repository.QuakeRepository
import kotlinx.coroutines.launch

class QuakeViewModel(private val repository: QuakeRepository) : ViewModel() {
    val quakes: LiveData<List<DatabaseQuake>> = repository.quakes.asLiveData()

    init {
        refreshQuakes()
    }

    private fun refreshQuakes() {
        viewModelScope.launch {
            repository.refreshQuakes()
        }
    }
}

class QuakeViewModelFactory(private val repository: QuakeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuakeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuakeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}