package com.project.travelmedrivers.ui.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
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
        val status = holder.status
        val company = holder.company
        source.text = travelList[listPosition].sourceAdders
        destination.text = travelList[listPosition].destinationAddress[0]
        date.text = travelList[listPosition].departureDate
        status.adapter = ArrayAdapter<Status>(
            getApplicationContext(), android.R.layout.simple_list_item_1, Status.values()
        )
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
        var status: Spinner
        var company: Spinner

        init {
            itemView.setOnClickListener(this)
            source = itemView.findViewById(R.id.tvSource) as TextView
            destination = itemView.findViewById(R.id.tvDestination) as TextView
            date = itemView.findViewById(R.id.tvDate) as TextView
            status = itemView.findViewById(R.id.sStatus) as Spinner
            company = itemView.findViewById(R.id.sCompany) as Spinner

        }

        override fun onClick(view: View?) {
            //Log.d("onclick", "onClick " + layoutPosition + " " + item.text)
        }
    }
}
