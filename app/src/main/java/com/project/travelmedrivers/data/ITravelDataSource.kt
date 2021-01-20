package com.project.travelmedrivers.data

import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.entities.Travel


interface ITravelDataSource {
    fun addTravel(travel: Travel)
    fun updateTravel(travel: Travel)
    fun getAllTravels(): MutableList<Travel>
    fun getIsSuccess(): MutableLiveData<Boolean>
    interface NotifyToTravelListListener {
        fun onTravelsChanged()
    }

    fun removeTravel(id: String)
    fun setNotifyToTravelListListener(l: NotifyToTravelListListener?)
}