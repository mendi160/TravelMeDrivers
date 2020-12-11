package com.project.travelmedrivers.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.project.travelmedrivers.utils.Address
import com.project.travelmedrivers.utils.Status
class Travel : java.io.Serializable{

    @Entity(tableName = "travel_table")
    class Travel {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 1000
            private set
            get() = field
        var name: String
            private set
            get() = field
        var phoneNumber: Int
            private set
            get() = field
        var email: String
            private set
            get() = field
        var sourceAdders: Address
            private set
            get() = field

        var destinationAddress: MutableList<Address>
            private set
            get() = field

        var passengers: Int
            private set
            get() = field

        var departureDate: String
            private set
            get() = field

        var returnDate: String
            set
            get() = field

        var status: Status
            private set
            get() = field
        var serviceProvider: Map<String, Boolean>
            private set
            get() = field

        constructor(
            id: Int,
            name: String,
            phoneNumber: Int,
            email: String,
            sourceAdders: Address,
            destinationAddress: MutableList<Address>,
            passengers: Int,
            departureDate: String,
            returnDate: String,
            status: Status,
            serviceProvider: Map<String, Boolean> = mapOf(" " to false),
        ) {
            this.id = id
            this.name = name
            this.phoneNumber = phoneNumber
            this.email = email
            this.sourceAdders = sourceAdders
            this.destinationAddress = destinationAddress
            this.passengers = passengers
            this.departureDate = departureDate
            this.returnDate = returnDate
            this.status = status
            this.serviceProvider = serviceProvider
        }
        //
    @Exclude
    fun toMap(): Map<String, Any>? {
        val result: HashMap<String, Any> = HashMap()
        result["name"] = name
        result["phoneNumber"] = phoneNumber
        result["email"] = email
        result["passengers"] = passengers
        result["departureDate"] =departureDate
        result["returnDate"] =(returnDate)
        result["SourceAdders"] = sourceAdders
        result["status"] = status.name
        return result
    }
    }
}