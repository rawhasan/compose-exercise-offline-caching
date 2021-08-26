package com.example.offlinecaching.database

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuakeDao {

    @Query("SELECT * FROM quake_table")
    fun getQuakes(): Flow<List<DatabaseQuake>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quakes: List<DatabaseQuake>)

    @Query("DELETE FROM quake_table")
    suspend fun deleteAll()
}

@Database(entities = [DatabaseQuake::class], version = 1, exportSchema = false)
public abstract class QuakeRoomDatabase : RoomDatabase() {

    abstract fun quakeDao(): QuakeDao

    companion object {

        @Volatile
        private var INSTANCE: QuakeRoomDatabase? = null

        fun getDatabase(context: Context): QuakeRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuakeRoomDatabase::class.java,
                        "quake_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}