package com.project.travelmedrivers.ui.gallery

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.utils.Status


class TravelArrayAdapter(
    private val listItemLayout: Int,
    private val travelList: List<Travel>
) :
    RecyclerView.Adapter<TravelArrayAdapter.ViewHolder>() {
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
        val date = holder.date
        //val confirm = holder.
        val company = holder.company
        holder.travel = travelList[listPosition]
        source.text = travelList[listPosition].sourceAdders
        destination.text = travelList[listPosition].destinationAddress[0]
        date.text = travelList[listPosition].departureDate
        company.adapter = ArrayAdapter<String>(
            getApplicationContext(),
            android.R.layout.simple_list_item_1,
            travelList[listPosition].serviceProvider.keys.toMutableList()
        )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var source: TextView
        var destination: TextView
        var date: TextView
        var bConfirm: Button
        var bRunning: Button
        var bFinished: Button
        var company: Spinner
        lateinit var travel: Travel

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSource) as TextView
            destination = itemView.findViewById(R.id.tvDestination) as TextView
            date = itemView.findViewById(R.id.tvDate) as TextView
            bConfirm = itemView.findViewById(R.id.bConfirm)
            bConfirm.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.RECEIVED
            }
            bRunning = itemView.findViewById(R.id.bRunning)
            bRunning.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.RUNNING
            }
            bFinished = itemView.findViewById(R.id.bFinished)
            bFinished.setOnClickListener {
                this@ViewHolder.travel
                travel.status = Status.CLOSED
            }
            company = itemView.findViewById(R.id.sCompany) as Spinner
            company.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View,
                    position: Int,
                    id: Long
                ) {
                    this@ViewHolder.travel
                    travel.serviceProvider[parentView?.getItemIdAtPosition(position)
                        .toString()] to true
                    bFinished.isEnabled = true
                    bRunning.isEnabled = true
                    bConfirm.isEnabled = true
                    Log.i("a", "a")
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // your code here
                }
            }
        }

        override fun onClick(v: View?) {
            Log.i("click","Click on linear layout")
        }
    }
}


