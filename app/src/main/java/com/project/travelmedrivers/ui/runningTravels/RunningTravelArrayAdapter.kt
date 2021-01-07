package com.project.travelmedrivers.ui.runningTravels

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel


class RunningTravelArrayAdapter(
    private val listItemLayout: Int,
    private val travelList: List<Travel>
) :
    RecyclerView.Adapter<RunningTravelArrayAdapter.ViewHolder>() {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    // get the size of the list
    override fun getItemCount(): Int {
        return travelList?.size ?: 0
    }

    // specify the row layout file and click for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(listItemLayout, parent, false)
        return ViewHolder(view)
    }

    // load data in each row element
    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val source = holder.source
        val destination = holder.destination
        val departureDate = holder.departureDate
        val returnDate = holder.returnDate
        holder.travel = travelList[listPosition]
        source.text = travelList[listPosition].sourceAdders
        destination.text = travelList[listPosition].destinationAddress[0]
        departureDate.text = travelList[listPosition].departureDate
        returnDate.text = travelList[listPosition].returnDate
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var source: TextView
        var destination: TextView
        var departureDate: TextView
        var returnDate: TextView
        lateinit var travel: Travel

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSourceRunning) as TextView
            destination = itemView.findViewById(R.id.tvDestinationRunning) as TextView
            departureDate = itemView.findViewById(R.id.tvDepartureDateRunning) as TextView
            returnDate = itemView.findViewById(R.id.tvReturnDateRunning) as TextView
        }

        override fun onClick(v: View?) {
            Log.i("click", "Click on linear layout")
        }
    }
}


