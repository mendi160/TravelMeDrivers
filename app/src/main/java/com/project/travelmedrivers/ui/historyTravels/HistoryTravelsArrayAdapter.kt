package com.project.travelmedrivers.ui.historyTravels

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel
import com.project.travelmedrivers.utils.Status


class HistoryTravelsArrayAdapter(
    var travelList: List<Travel?>,
    var viewModel: MainViewModel
) :
    RecyclerView.Adapter<HistoryTravelsArrayAdapter.ViewHolder>() {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    // get the size of the list
    override fun getItemCount(): Int {
        return travelList.size ?: 0
    }

    // specify the row layout file and click for each row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.closed_item_lv, parent, false)
        return ViewHolder(view)
    }

    // load data in each row element
    @SuppressLint("RestrictedApi", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, listPosition: Int) {
        val source = holder.source
        val destination = holder.destination
        val date = holder.departureDate
        val returnDate = holder.returnDate
        val bPay = holder.bPay
        holder.travel = travelList[listPosition]!!
        source.text = travelList[listPosition]!!.sourceAdders
        destination.text = travelList[listPosition]!!.destinationAddress[0]
        date.text = travelList[listPosition]!!.departureDate
        returnDate.text = travelList[listPosition]!!.returnDate.toString()
        if (travelList[listPosition]!!.status == Status.PAID) {
            bPay.isEnabled = false
            bPay.text="paid up"
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var source: TextView
        var destination: TextView
        var departureDate: TextView
        var returnDate: TextView
        var bPay: Button
        lateinit var travel: Travel

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSource) as TextView
            destination = itemView.findViewById(R.id.tvDestination) as TextView
            departureDate = itemView.findViewById(R.id.tvDepartureDate) as TextView
            returnDate = itemView.findViewById(R.id.tvReturnDate)
            bPay = itemView.findViewById(R.id.bPay)
            bPay.setOnClickListener {
                this.travel.status = Status.PAID
                viewModel.updateTravel(this.travel)
            }
        }

        override fun onClick(v: View?) {
            Log.i("click", "Click on linear layout")


        }


    }
}
