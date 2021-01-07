package com.project.travelmedrivers.data

import androidx.lifecycle.LiveData
import com.project.travelmedrivers.entities.Travel

interface IHistoryDataSource {
    fun addTravel(p: Travel)
    fun addTravels(travelList: List<Travel>)
    fun editTravel(p: Travel)
    fun deleteTravel(p: Travel)
    fun getAllTRavels(): LiveData<List<Travel>>
    fun clearTable()

}