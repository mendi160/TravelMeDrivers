package com.project.travelmedrivers.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.ITravelRepository
import com.project.travelmedrivers.repos.TravelRepository
import com.project.travelmedrivers.utils.AddressTool
import com.project.travelmedrivers.utils.Status
import com.project.travelmedrivers.utils.Util


class MainViewModel(p: Application) : AndroidViewModel(p) {
    private val repository: ITravelRepository

    var openTravelsFragment: MutableLiveData<List<Travel?>?>?
    var runningTravelsFragment: MutableLiveData<List<Travel?>?>?
    var closedTravelsFragment: MutableLiveData<List<Travel?>?>?

    init {
        repository = TravelRepository.getInstance(p)
        openTravelsFragment = MutableLiveData(listOf())
        runningTravelsFragment = MutableLiveData(listOf())
        closedTravelsFragment = MutableLiveData(listOf())
        repository.getAllTravels()?.observeForever {
            runningTravelsFragment?.postValue(it?.filter { travel ->
                travel!!.serviceProvider[Util.getCompanyKey()] == true
                        && (travel.status == Status.RUNNING || travel.status == Status.RECEIVED)
            })
            openTravelsFragment?.postValue(it?.filter { travel -> travel!!.status == Status.SENT })
        }
        repository.getLocalTravels().observeForever {
            closedTravelsFragment?.postValue(it)
        }

    }

    fun updateTravel(travel: Travel?) {
        repository.updateTravel(travel)
    }

    fun relevantTravels(radius: Int, location: LatLng, context: Context): List<Travel?> {
        return openTravelsFragment?.value!!.filter { travel ->
            AddressTool.calculateDistance(
                location,
                AddressTool.stringToLatLong(travel!!.sourceAdders)
            ) <= radius
        }
    }

    fun isSuccess(): MutableLiveData<Boolean> {
        return repository.getIsSuccess()
    }
}