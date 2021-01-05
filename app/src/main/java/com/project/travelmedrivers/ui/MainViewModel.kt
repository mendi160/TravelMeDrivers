package com.project.travelmedrivers.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.ITravelRepository
import com.project.travelmedrivers.repos.TravelRepository


class MainViewModel(p: Application) : AndroidViewModel(p) {
    private val repository: ITravelRepository

    //     var openTravelsFragment: MutableLiveData<List<Travel?>?>?
//     var runningTravelsFragment: MutableLiveData<List<Travel?>?>?
//     var closedTravelsFragment: MutableLiveData<List<Travel?>?>?
    init {
        repository = TravelRepository.getInstance(p)
//        openTravelsFragment=repository.getAllTravels()
//        runningTravelsFragment=repository.getAllTravels()
//        closedTravelsFragment=repository.getAllTravels()
//        repository.getAllTravels()?.observeForever {
//            openTravelsFragment?.postValue(repository.getAllTravels()!!.value?.filter { travel -> travel!!.status == Status.SENT } as List<Travel>?)
//            runningTravelsFragment?.postValue(repository.getAllTravels()!!.value?.filter { travel -> travel!!.status == Status.RUNNING } as List<Travel>?)
//            closedTravelsFragment?.postValue(repository.getAllTravels()!!.value?.filter { travel -> travel!!.status == Status.CLOSED } as List<Travel>?)
//        }

    }

    fun addTravel(travel: Travel?) {
        repository.addTravel(travel)
    }

    fun updateTravel(travel: Travel?) {
        repository.updateTravel(travel)
    }

    fun getAllOpenTravels() {
    }
//
//    fun relevantTravels(radius: Int, location: String, context: Context): List<Travel?> {
//        val latLong = AddressTool.getLocationFromAddress(context, location)
//        return openTravelsFragment?.value!!.filter { it ->
//            latLong?.let { it1 ->
//                AddressTool.getLocationFromAddress(
//                    context, it!!.sourceAdders
//                )?.let { it2 ->
//                    AddressTool.calculateDistance(it1, it2)
//                }
//            }!! <= radius
//        }
//    }

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