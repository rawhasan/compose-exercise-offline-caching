package com.example.offlinecaching

import android.app.Application
import androidx.work.*
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

        // run the background work only in wi-fi & broadband
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true) // run only when the battery is not low
            //.setRequiresCharging(true) // run only when the phone is in charging
//            .apply {
//                // works only in version 23 (Android 6 Marshmallow) or higher
//                // so check version of the device OS before running
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    setRequiresDeviceIdle(true) // run only if the device is idle
//                }
//            }
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}