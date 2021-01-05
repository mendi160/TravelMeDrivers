package com.project.travelmedrivers.ui.openTravels

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel
import com.project.travelmedrivers.utils.Util


class OpenTravelArrayAdapter(
    var travelList: List<Travel?>,
    var viewModel: MainViewModel
) :
    RecyclerView.Adapter<OpenTravelArrayAdapter.ViewHolder>() {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    // get the size of the list
    override fun getItemCount(): Int {
        return travelList?.size ?: 0
    }

    // specify the row layout file and click for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.open_item_lv, parent, false)
        return ViewHolder(view)
    }

    // load data in each row element
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val source = holder.source
        val destination = holder.destination
        val date = holder.date
        val passenger = holder.passenger
        holder.travel = travelList[listPosition]!!
        source.text = travelList[listPosition]!!.sourceAdders
        destination.text = travelList[listPosition]!!.destinationAddress[0]
        date.text = travelList[listPosition]!!.departureDate
        passenger.text = travelList[listPosition]!!.passengers.toString()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var source: TextView
        var destination: TextView
        var date: TextView
        var passenger: TextView
        var bSendOffer: Button
        lateinit var travel: Travel

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSource) as TextView
            destination = itemView.findViewById(R.id.tvDestination) as TextView
            date = itemView.findViewById(R.id.tvDate) as TextView
            passenger = itemView.findViewById(R.id.tvPassenger)
            bSendOffer = itemView.findViewById(R.id.bSendOffer)
            bSendOffer.setOnClickListener {
                val key = FirebaseAuth.getInstance().currentUser?.email?.let { it1 ->
                    Util.emailToKey(it1)
                }
                this@ViewHolder.travel.serviceProvider.plus(key to false)
                viewModel.updateTravel(this.travel)
            }
        }

        override fun onClick(v: View?) {
            Log.i("click", "Click on linear layout")

        }

    }
}



