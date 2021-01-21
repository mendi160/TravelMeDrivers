package com.project.travelmedrivers.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat

class Util {
    companion object {
        fun getCompanyKey(): String? {
            val u = FirebaseAuth.getInstance().currentUser
            if (u != null) {
                val temp = u.email
                return temp?.substring(0, temp.indexOf("@"))
            }
            return null
        }

        /**
         * This function compare between two dates.
         *  Return true if the first date equal or later
         */
        fun compareStringsOfDate(firstDate: String, secondDate: String): Boolean {
            return secondDate != "" && SimpleDateFormat("dd,MM,yyyy").parse(secondDate).time - SimpleDateFormat(
                "dd,MM,yyyy"
            ).parse(firstDate).time <= 0
        }
    }
}