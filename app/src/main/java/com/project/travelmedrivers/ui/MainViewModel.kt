package com.project.travelmedrivers.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.ITravelRepository
import com.project.travelmedrivers.repos.TravelRepository


class MainViewModel(p: Application?) : AndroidViewModel(p!!) {
    var repository: ITravelRepository
    fun addTravel(travel: Travel?) {
        repository.addTravel(travel)
    }

    fun updateTravel(travel: Travel?) {
        repository.updateTravel(travel)
    }

    val allTravels: MutableLiveData<List<Travel?>?>?
        get() = repository.getAllTravels()
    val isSuccess: MutableLiveData<Boolean?>?
        get() = repository.getIsSuccess()

    init {
        repository = TravelRepository.instance
    }
}