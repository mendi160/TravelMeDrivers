package com.project.travelmedrivers.ui.runningTravels


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


class RunningTravelsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    @SuppressLint("RestrictedApi")
    private lateinit var recyclerView: RecyclerView
    private var itemList = mutableListOf<Travel>()
    private lateinit var root: View

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_running_travels, container, false)
        // return super.onCreateView(inflater, container, savedInstanceState)
        return root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!
        recyclerView = root.findViewById(R.id.rvRunningTravel)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
            //adapter = RunningTravelArrayAdapter(R.layout.running_item_lv_, itemList)
        }
        viewModel.runningTravelsFragment?.observe(this, {
            itemList = (it as List<Travel>).toMutableList()
            recyclerView.adapter = RunningTravelArrayAdapter(R.layout.running_item_lv_, itemList)

        })
    }

}
