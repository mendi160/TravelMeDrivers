package com.project.travelmedrivers.entities

import androidx.room.*
import com.google.firebase.database.Exclude
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
    var phoneNumber: Int = 0
        set
        get() = field
    var email: String = ""
        set
        get() = field
    var sourceAdders: String = ""
        set
        get() = field

    @Ignore
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

    @Exclude
    fun toMap(): MutableMap<String, Any>? {
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

class RequestType {
    @TypeConverter
    fun getType(numeral: Int): Status? {
        return when (numeral) {
            0 -> Status.SENT
            1 -> Status.RECEIVED
            2 -> Status.RUNNING
            3 -> Status.CLOSED
            4 -> Status.PAID
            else -> null
        }
    }

    @TypeConverter
    fun getTypeInt(status: Status): Int? {
        return status.ordinal
    }
}

class CompanyConverter {
    @TypeConverter
    fun fromString(value: String?): MutableMap<String, Boolean>? {
        if (value == null || value.isEmpty()) return null
        val mapString =
            value.split(",").toTypedArray() //split map into array of (string,boolean) strings
        val hashMap: MutableMap<String, Boolean> = HashMap()
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
    fun asString(map: MutableMap<String?, Boolean?>?): String? {
        if (map == null) return null
        val mapString = StringBuilder()
        for ((key, value) in map.entries) mapString.append(
            key
        ).append(":").append(value).append(",")
        return mapString.toString()
    }
}