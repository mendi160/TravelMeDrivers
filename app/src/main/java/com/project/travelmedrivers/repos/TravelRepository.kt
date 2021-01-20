package com.project.travelmedrivers.repos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.data.HistoryDataSource
import com.project.travelmedrivers.data.IHistoryDataSource
import com.project.travelmedrivers.data.ITravelDataSource
import com.project.travelmedrivers.data.ITravelDataSource.NotifyToTravelListListener
import com.project.travelmedrivers.data.TravelDataSource
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.utils.Status


class TravelRepository private constructor(application: Application) : ITravelRepository {
    var travelDataSource: ITravelDataSource = TravelDataSource.instance

    private val historyDataSource: IHistoryDataSource
    val mutableLiveData = MutableLiveData<List<Travel?>?>()

    companion object {

        @Volatile
        private var INSTANCE: TravelRepository? = null

        fun getInstance(application: Application): TravelRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TravelRepository(application).also { INSTANCE = it }
            }
    }

    init {
        historyDataSource = HistoryDataSource(application.applicationContext)
        val notifyToTravelListListener: NotifyToTravelListListener =
            object : NotifyToTravelListListener {
                override fun onTravelsChanged() {
                    val travelList: List<Travel?> = travelDataSource.getAllTravels()
                    //mutableLiveData.value = travelList
                    mutableLiveData.postValue(travelList)
                    historyDataSource.clearTable()
                    historyDataSource.addTravels(travelList.filter { it -> it!!.status == Status.CLOSED || it.status == Status.PAID } as List<Travel>)
                }
            }
        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener)
    }

    override fun addTravel(travel: Travel?) {
        travelDataSource.addTravel(travel!!)
    }

    override fun updateTravel(travel: Travel?) {
        travelDataSource.updateTravel(travel!!)
    }

    override fun getAllTravels(): MutableLiveData<List<Travel?>?>? {
        return mutableLiveData
    }

    override fun getLocalTravels(): LiveData<List<Travel>> {
        return historyDataSource.getAllTravels()
    }

    override fun getIsSuccess(): MutableLiveData<Boolean> {
        return travelDataSource.getIsSuccess()
    }
}