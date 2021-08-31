package com.example.offlinecaching

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.offlinecaching.database.QuakeRoomDatabase
import com.example.offlinecaching.repository.QuakeRepository
import com.example.offlinecaching.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class QuakeApplication : Application() {
    private val database by lazy { QuakeRoomDatabase.getDatabase(this) }
    val repository by lazy { QuakeRepository(database.quakeDao()) }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        delayedInit()
    }

    /**
     * Run the long-running tasks inside a coroutine
     */
    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork() {
        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .build()

        /**
         * Test code to run the background work in every 15 minuites
         * Wipe emulator data before activating
         */
//        val repeatingRequest =
//            PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
//                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}