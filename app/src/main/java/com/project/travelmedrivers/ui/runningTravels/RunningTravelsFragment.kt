package com.project.travelmedrivers.ui.runningTravels


import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.TravelRepository


class RunningTravelsFragment : Fragment() {
    @SuppressLint("RestrictedApi")
    private val repo = TravelRepository.getInstance(Application())
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
        recyclerView = root.findViewById(R.id.rvRunningTravel)
        recyclerView.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
            adapter = RunningTravelArrayAdapter(R.layout.running_item_lv_, itemList)
        }
        repo.mutableLiveData.observe(this, {
            itemList = (it as List<Travel>).toMutableList()
            recyclerView.adapter = RunningTravelArrayAdapter(R.layout.running_item_lv_, itemList)

        })
    }

}
