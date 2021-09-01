package com.example.offlinecaching.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.offlinecaching.database.QuakeRoomDatabase.Companion.getDatabase
import com.example.offlinecaching.repository.QuakeRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.example.offlinecaching.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = QuakeRepository(database.quakeDao())

        try {
            repository.refreshQuakes()
            Log.d("RefreshDataWorker", "Work request for sync is run")
        } catch (e: HttpException) {
            Log.d("RefreshDataWorker", "Work request for sync failed...retrying...")
            return Result.retry()
        }

        return Result.success()
    }
}