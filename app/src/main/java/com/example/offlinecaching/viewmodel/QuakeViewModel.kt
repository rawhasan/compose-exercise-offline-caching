package com.example.offlinecaching.viewmodel

import androidx.lifecycle.*
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.repository.QuakeRepository
import kotlinx.coroutines.launch

enum class UsgsApiStatus { LOADING, ERROR, DONE }

class QuakeViewModel(private val repository: QuakeRepository) : ViewModel() {
    val quakes: LiveData<List<DatabaseQuake>> = repository.quakes.asLiveData()

    private var _status = MutableLiveData<UsgsApiStatus>(UsgsApiStatus.LOADING)
    val status: LiveData<UsgsApiStatus>
        get() = _status

    init {
        refreshQuakes()
    }

    private fun refreshQuakes() {
        _status.value = UsgsApiStatus.LOADING

        try {
            viewModelScope.launch {
                repository.refreshQuakes()
            }
            _status.value = UsgsApiStatus.DONE
        } catch (e: Exception) {
            _status.value = UsgsApiStatus.ERROR
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