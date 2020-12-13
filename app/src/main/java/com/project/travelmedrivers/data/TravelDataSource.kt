package com.project.travelmedrivers.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.travelmedrivers.entities.Travel

class TravelDataSource : ITravelDataSource {
    var travelsList = mutableListOf<Travel>()
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
                Log.i("fff","fff")
//                val travel: Travel = snapshot.children.forEach { it ->
//                    it.children.forEach { it ->
//                        travels.add(it)
//                    }
//                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })

    override fun getAllTravels(): MutableList<Travel> {
        TODO("Not yet implemented")
    }


}