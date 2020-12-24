package com.project.travelmedrivers.repos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.project.travelmedrivers.data.HistoryDataSource
import com.project.travelmedrivers.data.IHistoryDataSource
import com.project.travelmedrivers.data.ITravelDataSource
import com.project.travelmedrivers.data.ITravelDataSource.NotifyToTravelListListener
import com.project.travelmedrivers.data.TravelDataSource
import com.project.travelmedrivers.entities.Travel


class TravelRepository private constructor(application: Application) : ITravelRepository {
    var travelDataSource: ITravelDataSource = TravelDataSource.getInstance()!!
    private val historyDataSource: IHistoryDataSource
    private val mutableLiveData = MutableLiveData<List<Travel?>?>()
    override fun addTravel(travel: Travel?) {
        travelDataSource.addTravel(travel!!)
    }

    override fun updateTravel(travel: Travel?) {
        travelDataSource.updateTravel(travel!!)
    }

    override fun getAllTravels(): MutableLiveData<List<Travel?>?>? {
        return mutableLiveData
    }

    override fun getIsSuccess(): MutableLiveData<Boolean?> {
        return travelDataSource.getIsSuccess()
    }

    companion object {
        private var instance: TravelRepository? = null
        fun getInstance(application: Application): TravelRepository? {
            if (instance == null) instance = TravelRepository(application)
            return instance
        }
    }

    init {
        historyDataSource = HistoryDataSource(application.applicationContext)
        val notifyToTravelListListener: NotifyToTravelListListener =
            object : NotifyToTravelListListener {
                override fun onTravelsChanged() {
                    val travelList: List<Travel?> = travelDataSource.getAllTravels()
                    mutableLiveData.value = travelList
                    historyDataSource.clearTable()
                    historyDataSource.addTravels(travelList as List<Travel>)
                }
            }
        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener)
    }
}