package com.project.travelmedrivers.ui.historyTravels

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel

class HistoryTravelsFragment : Fragment() {
    lateinit var rvClosedTravels: RecyclerView
   // lateinit var arrayAdapter: HistoryTravelsArrayAdapter
    private var closedTravelList = mutableListOf<Travel>()
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_travels, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!
        //arrayAdapter = HistoryTravelsArrayAdapter(closedTravelList, viewModel)
        rvClosedTravels = view.findViewById(R.id.rvClosedTravel)
        // arrayAdapter = OpenTravelArrayAdapter(openTravelList)
        rvClosedTravels.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
            // adapter = arrayAdapter
        }
        viewModel.closedTravelsFragment?.observe(this, {
            if (it?.size != 0) {
                closedTravelList = it as MutableList<Travel>
                rvClosedTravels.adapter = HistoryTravelsArrayAdapter(closedTravelList, viewModel)
            }
        })
    }
}