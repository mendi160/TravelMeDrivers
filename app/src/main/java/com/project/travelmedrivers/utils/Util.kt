package com.project.travelmedrivers.utils

import com.google.firebase.auth.FirebaseAuth

class Util {
    companion object {
        fun emailToKey(email: String): String? {
            var u = FirebaseAuth.getInstance().currentUser
            if (u != null) {
                val temp = u.email
                return temp?.substring(0, temp.indexOf("@"))
            }
            return null
        }
    }
}