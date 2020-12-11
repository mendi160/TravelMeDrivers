package com.project.travelmedrivers.data

import android.widget.CalendarView
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.travelmedrivers.entities.Travel

class TravelDataSource :ITravelDataSource {
    var travels = mutableListOf<DataSnapshot>()
    var ref =FirebaseDatabase.getInstance().getReference("Travels").addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.children.forEach { it -> it.children.forEach{it->
                travels.add(it)
             } }
   }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
    override fun getAllTravels(): MutableList<Travel> {
        TODO("Not yet implemented")
    }


}