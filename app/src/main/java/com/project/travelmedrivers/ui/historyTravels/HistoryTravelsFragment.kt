package com.project.travelmedrivers.ui.historyTravels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.travelmedrivers.R

class HistoryTravelsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history_travels, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        return root
    }
}