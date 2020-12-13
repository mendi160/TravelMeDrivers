package com.project.travelmedrivers.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.firebase.database.Exclude
import com.project.travelmedrivers.utils.Status

@Entity(tableName = "travel_table")
class Travel {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1000
        private set
        get() = field
     var name: String=""
         set
        get() = field
    var phoneNumber: Int=0
         set
        get() = field
    var email: String=""
         set
        get() = field
    var sourceAdders: String=""
         set
        get() = field

    var destinationAddress= mutableListOf<String>()
        set
        get() = field

    var passengers: Int=0
        set
        get() = field

    var departureDate: String=""
        set
        get() = field

    var returnDate: String=""
        set
        get() = field

    var status=Status.SENT
        set
        get() = field
    var serviceProvider= mapOf("" to false)
        set
        get() = field
    constructor()
//    constructor(
//        id: Int,
//        name: String,
//        phoneNumber: Int,
//        email: String,
//        sourceAdders: String,
//        destinationAddress: MutableList<String>,
//        passengers: Int,
//        departureDate: String,
//        returnDate: String,
//        status: Status,
//        @TypeConverters(CompanyConverter::class)
//        serviceProvider: Map<String, Boolean> = mapOf(" " to false),
//    ) {
//        this.id = id
//        this.name = name
//        this.phoneNumber = phoneNumber
//        this.email = email
//        this.sourceAdders = sourceAdders
//        this.destinationAddress = destinationAddress
//        this.passengers = passengers
//        this.departureDate = departureDate
//        this.returnDate = returnDate
//        this.status = status
//        this.serviceProvider = serviceProvider
//    }

    //
    @Exclude
    fun toMap(): Map<String, Any>? {
        val result: HashMap<String, Any> = HashMap()
        result["name"] = name
        result["phoneNumber"] = phoneNumber
        result["email"] = email
        result["passengers"] = passengers
        result["departureDate"] = departureDate
        result["returnDate"] = (returnDate)
        result["SourceAdders"] = sourceAdders
        result["status"] = status.name
        return result
    }
}

class CompanyConverter {
    @TypeConverter
    fun fromString(value: String?): HashMap<String, Boolean>? {
        if (value == null || value.isEmpty()) return null
        val mapString =
            value.split(",").toTypedArray() //split map into array of (string,boolean) strings
        val hashMap: HashMap<String, Boolean> = HashMap()
        for (s1 in mapString)  //for all (string,boolean) in the map string
        {
            if (s1.isNotEmpty()) { //is empty maybe will needed because the last char in the string is ","
                val s2 = s1.split(":")
                    .toTypedArray() //split (string,boolean) to company string and boolean string.
                val aBoolean = java.lang.Boolean.parseBoolean(s2[1])
                hashMap[s2[0]] = aBoolean
            }
        }
        return hashMap
    }

    @TypeConverter
    fun asString(map: HashMap<String?, Boolean?>?): String? {
        if (map == null) return null
        val mapString = StringBuilder()
        for ((key, value) in map.entries) mapString.append(
            key
        ).append(":").append(value).append(",")
        return mapString.toString()
    }
}