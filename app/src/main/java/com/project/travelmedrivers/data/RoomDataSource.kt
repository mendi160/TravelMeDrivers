package com.project.travelmedrivers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.utils.CompanyConverter
import com.project.travelmedrivers.utils.DestinationAddresses
import com.project.travelmedrivers.utils.RequestType


@Database(entities = [Travel::class], version = 1, exportSchema = false)
@TypeConverters(CompanyConverter::class, RequestType::class, DestinationAddresses::class)
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