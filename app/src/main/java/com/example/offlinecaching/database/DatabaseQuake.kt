package com.example.offlinecaching.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quake_table")
data class DatabaseQuake(
        @PrimaryKey
        val id: String,
        val mag: Double,
        val near: String,
        val place: String,
        val date: String,
        val time: String,
        val url: String,
        val lng: Double,
        val lat: Double
)

