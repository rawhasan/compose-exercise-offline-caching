package com.example.offlinecaching.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.offlinecaching.database.DatabaseQuake
import com.example.offlinecaching.database.QuakeDao
import com.example.offlinecaching.network.NetworkQuake
import com.example.offlinecaching.network.UsgsApi
import com.example.offlinecaching.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class QuakeRepository(private val quakeDao: QuakeDao) {

    val quakes: Flow<List<DatabaseQuake>> = quakeDao.getQuakes()

    // update database only if the latest quake date amd time is not the same sa the database
    // fetch the latest data from the API, empty database, and insert all to the database if new data
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshQuakes() {
        var quakeList = NetworkQuake(listOf())

        withContext(Dispatchers.IO) {
            try {
                val quakeLatestNetwork = UsgsApi.retrofitService.getLatestQuake().asDatabaseModel()
                val quakeLatestDatabase = quakeDao.getLatestQuake().first()

                Log.d("QuakeRepository", "Network latest: $quakeLatestNetwork")
                Log.d("QuakeRepository", "Database latest: $quakeLatestDatabase")

                if (quakeLatestDatabase == null ||
                    (quakeLatestNetwork[0].date != quakeLatestDatabase.date
                            && quakeLatestNetwork[0].time != quakeLatestDatabase.time)
                ) {
                    Log.d("QuakeRepository", "Data updated")

                    quakeList = UsgsApi.retrofitService.getQuakes()

                    quakeDao.deleteAll()
                    quakeDao.insertAll(quakeList.asDatabaseModel())
                } else
                    Log.d("QuakeRepository", "No update")
            } catch (e: Exception) {
                quakeList = NetworkQuake(listOf())
                Log.d("QuakeRepository", "Error: ${e.message}")
            }
        }
    }
}