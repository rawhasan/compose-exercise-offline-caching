package com.example.offlinecaching.repository

import android.util.Log
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.database.QuakeDao
import com.example.offlinecaching.network.NetworkQuake
import com.example.offlinecaching.network.UsgsApi
import com.example.offlinecaching.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class QuakeRepository(private val quakeDao: QuakeDao) {

    val quakes: Flow<List<DatabaseQuake>> = quakeDao.getQuakes()

    // fetch the latest data from the API, empty database, and insert all to the database
    suspend fun refreshQuakes() {
        var quakeList = NetworkQuake(listOf())

        withContext(Dispatchers.IO) {
            try {
                quakeList = UsgsApi.retrofitService.getQuakes()
                quakeDao.deleteAll()
                quakeDao.insertAll(quakeList.asDatabaseModel())
            } catch (e: Exception) {
                quakeList = NetworkQuake(listOf())
                Log.d("QuakeRepository", "Error: ${e.message}")
            }
        }
    }
}