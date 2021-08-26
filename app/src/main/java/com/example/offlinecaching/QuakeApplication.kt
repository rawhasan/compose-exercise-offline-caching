package com.example.offlinecaching

import android.app.Application
import com.example.offlinecaching.database.QuakeRoomDatabase
import com.example.offlinecaching.repository.QuakeRepository

class QuakeApplication : Application() {
    private val database by lazy { QuakeRoomDatabase.getDatabase(this) }
    val repository by lazy { QuakeRepository(database.quakeDao()) }
}