package com.project.travelmedrivers.data

import com.project.travelmedrivers.entities.Travel

interface ITravelDataSource {
    fun getAllTravels():MutableList<Travel>
}