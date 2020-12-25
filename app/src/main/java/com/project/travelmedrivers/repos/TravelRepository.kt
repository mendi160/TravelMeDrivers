package com.project.travelmedrivers.repos

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.project.travelmedrivers.data.HistoryDataSource
import com.project.travelmedrivers.data.IHistoryDataSource
import com.project.travelmedrivers.data.ITravelDataSource
import com.project.travelmedrivers.data.ITravelDataSource.NotifyToTravelListListener
import com.project.travelmedrivers.data.TravelDataSource
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.utils.AddressTool
import com.project.travelmedrivers.utils.Status


class TravelRepository private constructor(application: Application) : ITravelRepository {
    var travelDataSource: ITravelDataSource = TravelDataSource.getInstance()!!
    private val historyDataSource: IHistoryDataSource
    private val mutableLiveData = MutableLiveData<List<Travel?>?>()
    private var userTravels: MutableList<Travel> = mutableListOf()

    init {
        historyDataSource = HistoryDataSource(application.applicationContext)
        val notifyToTravelListListener: NotifyToTravelListListener =
            object : NotifyToTravelListListener {
                override fun onTravelsChanged() {
                    val travelList: List<Travel?> = travelDataSource.getAllTravels()
                    mutableLiveData.value = travelList
                    historyDataSource.clearTable()
                    historyDataSource.addTravels(travelList as List<Travel>)
                    userTravels()
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

    fun userTravels() {
        val userID = FirebaseAuth.getInstance().currentUser?.email
        if (userID != null) {
            userTravels =
                mutableLiveData.value?.filter { it -> it?.email == userID && it.status != Status.CLOSED } as MutableList<Travel>
        }
    }

    fun confirmCompany(travel: Travel, companyEmail: String) {
        travel.serviceProvider[companyEmail] to true;
        travelDataSource.updateTravel(travel)
    }

    fun changeStatus(travel: Travel) {
        travel.status = Status.RECEIVED
        travelDataSource.updateTravel(travel)
    }

    fun relevantTravels(radius: Int, location: String, context: Context): List<Travel?> {
        val latLong = AddressTool.getLocationFromAddress(context, location)
        val tempList =
            mutableLiveData.value!!.filter { it -> it!!.status == Status.RECEIVED || it.status == Status.SENT }
        return tempList.filter { it ->
            latLong?.let { it1 ->
                AddressTool.getLocationFromAddress(
                    context, it!!.sourceAdders
                )?.let { it2 ->
                    AddressTool.calculateDistance(it1, it2)
                }
            }!! <= radius
        }
    }

    fun updateServiceProvider(travel: Travel) {
        travel.serviceProvider[FirebaseAuth.getInstance().currentUser?.email] to false
        travelDataSource.updateTravel(travel)
    }
}