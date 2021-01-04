package com.project.travelmedrivers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.travelmedrivers.entities.CompanyConverter
import com.project.travelmedrivers.entities.Travel


@Database(entities = [Travel::class], version = 1, exportSchema = false)
@TypeConverters(
    CompanyConverter::class
)
abstract class RoomDataSource : RoomDatabase() {
    abstract val travelDao: TravelDao?
    companion object {
        private const val DATABASE_NAME = "Localdatabase"
        private var database: RoomDataSource? = null
        fun getInstance(context: Context?): RoomDataSource? {
            if (database == null) database = context?.let {
                Room.databaseBuilder(
                    it,
                    RoomDataSource::class.java, DATABASE_NAME
                ).allowMainThreadQueries().build()
            }
            return database
        }
    }
}