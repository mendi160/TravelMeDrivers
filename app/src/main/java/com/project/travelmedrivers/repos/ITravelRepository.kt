package com.project.travelmedrivers.repos

import androidx.lifecycle.MutableLiveData

import com.project.travelmedrivers.entities.Travel


interface ITravelRepository {
    fun addTravel(travel: Travel?)
    fun updateTravel(travel: Travel?)
    fun getAllTravels(): MutableLiveData<List<Travel?>?>?
    fun getIsSuccess(): MutableLiveData<Boolean?>?
}