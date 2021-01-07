package com.project.travelmedrivers.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.project.travelmedrivers.entities.Travel

class HistoryDataSource(context: Context?) : IHistoryDataSource {
    private var travelDao: TravelDao

    init {
        val database = RoomDataSource.getInstance(context)
        travelDao = database!!.travelDao!!
        travelDao.clear()
    }

    fun getTravel(id: String?): LiveData<Travel?>? {
        return travelDao.get(id)
    }

    override fun addTravel(p: Travel) {
        travelDao.insert(p)
    }

    override fun addTravels(travelList: List<Travel>) {
        travelDao.insert(travelList)
    }

    override fun editTravel(p: Travel) {
        travelDao.update(p)
    }

    override fun deleteTravel(p: Travel) {
        travelDao.delete(p)
    }

    override fun getAllTRavels(): LiveData<List<Travel>> {
        return travelDao.getAll()
    }

    override fun clearTable() {
        travelDao.clear()
    }
}