package com.project.travelmedrivers.ui.gallery


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
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.repos.TravelRepository


class RegisteredTravelsFragment : Fragment() {
    @SuppressLint("RestrictedApi")
    private val repo = TravelRepository(getApplicationContext() as Application)
    private lateinit var recyclerView: RecyclerView
    private var itemList = mutableListOf<Travel>()
    private lateinit var galleryViewModel: GalleryViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_registered, container, false)
        recyclerView = root.findViewById(R.id.rvRegisteredTravel)
        recyclerView.apply {
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.layoutManager = LinearLayoutManager(getApplicationContext())
            recyclerView.adapter = TravelArrayAdapter(R.layout.registered_item_lv_, itemList)
        }
        repo.mutableLiveData.observe(this, {

            itemList = (it as List<Travel>).toMutableList()
            recyclerView.adapter?.notifyDataSetChanged()

        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }


}