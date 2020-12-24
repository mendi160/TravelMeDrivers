package com.project.travelmedrivers.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.travelmedrivers.data.ITravelDataSource.NotifyToTravelListListener
import com.project.travelmedrivers.entities.Travel

class TravelDataSource : ITravelDataSource {
    companion object {
        private var instance: TravelDataSource? = null
        fun getInstance(): TravelDataSource? {
            if (instance == null) instance = TravelDataSource()
            return instance
        }
    }

    var travelsList = mutableListOf<Travel>()
    private val isSuccess = MutableLiveData<Boolean?>()
    private val uId = FirebaseAuth.getInstance().uid
    private var userTravels: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Travels/$uId")
    private var notifyToTravelListListener: NotifyToTravelListListener? = null
    var ref = FirebaseDatabase.getInstance().getReference("Travels").addValueEventListener(object :
        ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            travelsList.clear()
            if (dataSnapshot.exists()) {
                for (snapshot in dataSnapshot.children) {
                    for (travels in snapshot.children) {
                        val travel = travels.getValue(Travel::class.java)
                        if (travel != null) {
                            travelsList.add(travel)
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

    override fun addTravel(travel: Travel) {
        val id = userTravels.push().key
        if (id != null) {
            travel.id = id
        }
        userTravels.setValue(travel)
            .addOnSuccessListener { isSuccess.postValue(true) }
            .addOnFailureListener { isSuccess.postValue(false) }
    }

    override fun updateTravel(travel: Travel) {
        removeTravel(travel.id)
        addTravel(travel)
    }

    override fun getAllTravels(): MutableList<Travel> {
        return travelsList
    }

    override fun getIsSuccess(): MutableLiveData<Boolean?> {
        return isSuccess
    }

    override fun removeTravel(id: String) {
        userTravels.child(id).removeValue()
            .addOnSuccessListener { Log.i("remove", "The travel $id removed") }
            .addOnFailureListener {
                Log.i("remove", it.message.toString())
            }
    }

    override fun setNotifyToTravelListListener(l: ITravelDataSource.NotifyToTravelListListener?) {
        notifyToTravelListListener = l;
    }
}