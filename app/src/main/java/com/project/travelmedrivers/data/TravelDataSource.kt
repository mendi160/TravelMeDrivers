package com.project.travelmedrivers.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.project.travelmedrivers.data.ITravelDataSource.NotifyToTravelListListener
import com.project.travelmedrivers.entities.Travel

class TravelDataSource private constructor() : ITravelDataSource {
    private object HOLDER {
        val INSTANCE = TravelDataSource()
    }

    companion object {
        val instance: TravelDataSource by lazy { HOLDER.INSTANCE }
    }

    private var referenceMap = mutableMapOf<String, DatabaseReference>()
    private val database = FirebaseDatabase.getInstance()
    var travelsList = mutableListOf<Travel>()
    private val isSuccess = MutableLiveData<Boolean>()

    private var notifyToTravelListListener: NotifyToTravelListListener? = null
    var requestCount: Int = 0
    private val countRef = database.getReference("counter");

    init {
        database.getReference("Travels").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                travelsList.clear()
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        for (travels in snapshot.children) {
                            val travel = travels.getValue(Travel::class.java)
                            if (travel != null) {
                                referenceMap[travel.id] = travels.ref
                                travelsList.add(0, travel)
                            }
                        }
                    }
                    Log.i("Change", "Data chanced")
                }
                notifyToTravelListListener?.onTravelsChanged();
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        countRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                requestCount = snapshot.child("val").value.toString().toInt()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun addTravel(travel: Travel) {
        TODO("Not yet implemented")
    }

    override fun updateTravel(travel: Travel) {
        referenceMap[travel.id]!!.setValue(travel)
            .addOnSuccessListener { isSuccess.postValue(true) }
            .addOnFailureListener { isSuccess.postValue(false) }
    }

    override fun getAllTravels(): MutableList<Travel> {
        return travelsList
    }

    override fun getIsSuccess(): MutableLiveData<Boolean> {
        return isSuccess
    }

    override fun removeTravel(id: String) {
        TODO("Not yet implemented")
    }

    override fun setNotifyToTravelListListener(l: NotifyToTravelListListener?) {
        notifyToTravelListListener = l;
    }
}