package com.project.travelmedrivers.ui.openTravels

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel

class OpenTravelsFragment : Fragment() {
    private lateinit var latlng: LatLng
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var rvOpenTravels: RecyclerView
    private lateinit var arrayAdapter: OpenTravelArrayAdapter
    private lateinit var etLocation: EditText
    private lateinit var editDistance: EditText
    private lateinit var bFilter: Button
    private lateinit var pbLoading: ProgressBar
    private var openTravelList = mutableListOf<Travel>()
    private lateinit var viewModel: MainViewModel
    private lateinit var markerNewTravel: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_travels, container, false)
    }

    @SuppressLint("FragmentLiveDataObserve", "UseRequireInsteadOfGet", "CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }!!
        markerNewTravel = this.activity
            ?.getSharedPreferences("markerNewTravel", Context.MODE_PRIVATE)!!
        editor = markerNewTravel.edit()
        pbLoading = view.findViewById(R.id.pbLoading)
        arrayAdapter = OpenTravelArrayAdapter(openTravelList, viewModel, markerNewTravel)
        etLocation = view.findViewById<EditText>(R.id.etLocation)
        editDistance = view.findViewById<EditText>(R.id.etDistance)
        bFilter = view.findViewById(R.id.bFilter)
        bFilter.setOnClickListener {
            if (etLocation.text.toString() != "" && editDistance.text.toString() != "") {
                RelevantTravels().execute()
            } else {
                (rvOpenTravels.adapter as OpenTravelArrayAdapter).travelList = openTravelList
                (rvOpenTravels.adapter as OpenTravelArrayAdapter).notifyDataSetChanged()
            }

        }
        etLocation.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) autocomplete() }
        rvOpenTravels = view.findViewById(R.id.rvOpenTravel)
        rvOpenTravels.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
        }
        viewModel.openTravelsFragment?.observe(this, {
            if (it?.size != 0) {
                openTravelList = it as MutableList<Travel>
                rvOpenTravels.adapter =
                    OpenTravelArrayAdapter(openTravelList, viewModel, markerNewTravel!!)
                for (travel in openTravelList) {
                    if (!markerNewTravel.contains(travel.id)) {
                        editor.putBoolean(travel.id, true)
                        editor.apply()
                    }
                    pbLoading.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("RestrictedApi")
    fun autocomplete() {
        val api = "AIzaSyBUPxQMO2iI0DS_WTeetlcND9mpWaUCyyY"
        Places.initialize(getApplicationContext(), api)
        val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
        // Specify the types of place data to return.

        // Set up a PlaceSelectionListener to handle the response.

        // Start the autocomplete intent.
        val intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setCountries(
                mutableListOf("IL")
            )
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
                        latlng = place.latLng!!
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

    override fun onStop() {
        super.onStop()
        for (map in markerNewTravel.all) {
            editor.putBoolean(map.key, false)
            editor.apply()
        }
        Log.i("yyyyy", "yyyy")
    }

    inner class RelevantTravels() : AsyncTask<Unit, Unit, Unit>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pbLoading.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: Unit?) {
            arrayAdapter.travelList = viewModel.relevantTravels(
                editDistance.text.toString().toInt(),
                latlng,
                requireActivity().applicationContext
            )
        }

        override fun onPostExecute(result: Unit?) {
            pbLoading.visibility = View.GONE
            rvOpenTravels.adapter = arrayAdapter
        }
    }
}