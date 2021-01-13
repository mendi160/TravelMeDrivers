package com.project.travelmedrivers.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.project.travelmedrivers.utils.CompanyConverter
import com.project.travelmedrivers.utils.DestinationAddresses
import com.project.travelmedrivers.utils.RequestType
import com.project.travelmedrivers.utils.Status

@Entity(tableName = "travels")
class Travel() {
    @PrimaryKey
    var id: String = ""
        set
        get() = field
    var name: String = ""
        set
        get() = field
    var phoneNumber: String = ""
        set
        get() = field
    var email: String = ""
        set
        get() = field
    var sourceAdders: String = ""
        set
        get() = field

    @TypeConverters(DestinationAddresses::class)
    var destinationAddress = mutableListOf<String>()
        set
        get() = field

    var passengers: Int = 0
        set
        get() = field

    var departureDate: String = ""
        set
        get() = field

    var returnDate: String = ""
        set
        get() = field

    @TypeConverters(RequestType::class)
    var status = Status.SENT
        set
        get() = field

    @TypeConverters(CompanyConverter::class)
    var serviceProvider = mutableMapOf<String, Boolean>()
        set
        get() = field
}