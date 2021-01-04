package com.project.travelmedrivers.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.ITravelRepository
import com.project.travelmedrivers.repos.TravelRepository


class MainViewModel(p: Application) : AndroidViewModel(p) {
    private val repository: ITravelRepository
    lateinit var registeredTravelsFragment: MutableLiveData<List<Travel>>
    lateinit var runningTravelsFragment: MutableLiveData<List<Travel>>
    lateinit var closedTravelsFragment: MutableLiveData<List<Travel>>

    init {
        repository = TravelRepository.getInstance(p)

    }

    fun addTravel(travel: Travel?) {
        repository.addTravel(travel)
    }

    fun updateTravel(travel: Travel?) {
        repository.updateTravel(travel)
    }

    fun getAllOpenTravels() {
    }

    fun getRelevantOpenTravels(distance: Double, location: String) {
        getAllOpenTravels()
    }

    fun getRunningTravels() {
    }

    fun getClosedTravels() {
    }

    fun getAllTravels(): MutableLiveData<List<Travel?>?>? {
        return repository.getAllTravels()
    }

    fun isSuccess(): MutableLiveData<Boolean?>? {
        return repository.getIsSuccess()
    }
}