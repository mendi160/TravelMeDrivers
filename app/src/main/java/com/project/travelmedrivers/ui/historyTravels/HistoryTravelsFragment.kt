package com.project.travelmedrivers.ui.historyTravels

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.project.travelmedrivers.R
import com.project.travelmedrivers.entities.Travel
import com.project.travelmedrivers.ui.MainViewModel
import com.project.travelmedrivers.utils.Status
import com.project.travelmedrivers.utils.Util
import java.util.*


class HistoryTravelsFragment : Fragment() {
    lateinit var rvClosedTravels: RecyclerView
    private var closedTravelList = mutableListOf<Travel>()
    private var filteredList = listOf<Travel>()
    lateinit var viewModel: MainViewModel
    lateinit var dpFrom: TextView
    lateinit var dpTo: TextView
    lateinit var bFilter: Button
    lateinit var fabFilterDelete: ExtendedFloatingActionButton
    lateinit var swPaidOnly: Switch
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
        dpFrom = view.findViewById(R.id.dpFrom)
        dpFrom.setOnClickListener {
            pickDate(it as TextView)
        }
        fabFilterDelete = view.findViewById(R.id.fabDelete)
        fabFilterDelete.setOnClickListener {
            dpFrom.text = ""
            dpTo.text = ""
            swPaidOnly.isChecked = false
            filteredList = listOf()

            rvClosedTravels.adapter = HistoryTravelsArrayAdapter(closedTravelList, viewModel)
        }
        dpTo = view.findViewById(R.id.dpTo)
        dpTo.setOnClickListener {
            pickDate(it as TextView)
        }
        bFilter = view.findViewById(R.id.bFilter)
        swPaidOnly = view.findViewById(R.id.swPaidOnly)
        swPaidOnly.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (filteredList.isEmpty()) {
                    filteredList = closedTravelList.filter { it -> it.status == Status.PAID }
                    rvClosedTravels.adapter = HistoryTravelsArrayAdapter(filteredList, viewModel)
                } else {
                    filteredList = filteredList.filter { it -> it.status == Status.PAID }
                    rvClosedTravels.adapter = HistoryTravelsArrayAdapter(filteredList, viewModel)
                }
            } else
                if (filteredList.isEmpty())
                    rvClosedTravels.adapter =
                        HistoryTravelsArrayAdapter(closedTravelList, viewModel)
                else
                    rvClosedTravels.adapter =
                        HistoryTravelsArrayAdapter(filteredList, viewModel)

        }
        bFilter.setOnClickListener {

            filteredList = closedTravelList.filter { it ->
                if (swPaidOnly.isChecked)
                    (Util.compareStringsOfDate(
                        it.returnDate,
                        dpFrom.text.toString()
                    ) && Util.compareStringsOfDate(
                        dpTo.text.toString(),
                        it.returnDate
                    ) && it.status == Status.PAID)
                else
                    (Util.compareStringsOfDate(
                        it.returnDate,
                        dpFrom.text.toString()
                    ) && Util.compareStringsOfDate(dpTo.text.toString(), it.returnDate))
            }
            rvClosedTravels.adapter = HistoryTravelsArrayAdapter(filteredList, viewModel)
        }
        rvClosedTravels = view.findViewById(R.id.rvClosedTravel)
        rvClosedTravels.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(activity)
        }
        viewModel.closedTravelsFragment?.observe(this, {
            if (it?.size != 0) {
                closedTravelList = it as MutableList<Travel>
                rvClosedTravels.adapter = HistoryTravelsArrayAdapter(closedTravelList, viewModel)
            }
        })
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun pickDate(textV: TextView) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH).toInt()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this.requireActivity(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                textV.text = "" + dayOfMonth + "," + (monthOfYear.toInt() + 1) + "," + year
            },
            year,
            month,
            day
        )
        dpd.show()
    }


}
