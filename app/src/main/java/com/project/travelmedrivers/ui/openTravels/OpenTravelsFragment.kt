package com.project.travelmedrivers.ui.openTravels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel

class OpenTravelsFragment : Fragment() {
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    lateinit var rvOpenTravels: RecyclerView
    lateinit var arrayAdapter: OpenTravelArrayAdapter
    lateinit var etLocation: EditText
    lateinit var editDistance: EditText
    lateinit var bFilter: Button
    private var openTravelList = mutableListOf<Travel>()
    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_travels, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!
        arrayAdapter = OpenTravelArrayAdapter(openTravelList, viewModel)
        etLocation = view.findViewById<EditText>(R.id.etLocation)
        editDistance = view.findViewById<EditText>(R.id.etDistance)
        bFilter = view.findViewById(R.id.bFilter)
        bFilter.setOnClickListener {
            if (etLocation.text.toString() != "" && editDistance.text.toString() != "") {
                val t = Thread {
                    arrayAdapter.travelList = viewModel.relevantTravels(
                        editDistance.text.toString().toInt(),
                        etLocation.text.toString(),
                        requireActivity().applicationContext
                    )
                }
                t.start()
                while (t.isAlive);
                rvOpenTravels.adapter = arrayAdapter
            } else {
                rvOpenTravels.adapter = OpenTravelArrayAdapter(openTravelList, viewModel)
            }
        }
        etLocation.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) autocomplete() }
        rvOpenTravels = view.findViewById(R.id.rvOpenTravel)
        // arrayAdapter = OpenTravelArrayAdapter(openTravelList)
        rvOpenTravels.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
            // adapter = arrayAdapter
        }
        viewModel.openTravelsFragment?.observe(this, {
            if (it?.size != 0) {

                openTravelList = it as MutableList<Travel>
                rvOpenTravels.adapter = OpenTravelArrayAdapter(openTravelList, viewModel)
            }


        })


    }

    @SuppressLint("RestrictedApi")
    fun autocomplete() {
        val api = "AIzaSyBUPxQMO2iI0DS_WTeetlcND9mpWaUCyyY"
        Places.initialize(getApplicationContext(), api)
        val fields = listOf(Place.Field.ADDRESS)

        // Specify the types of place data to return.

        // Set up a PlaceSelectionListener to handle the response.

        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(getApplicationContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        etLocation.clearFocus()
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        // Log.i("test", "Place: ${place.name}, ${place.id}")
                        etLocation.setText(place.address.toString())
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        status.statusMessage?.let { it1 -> Log.i("test", it1) }
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}