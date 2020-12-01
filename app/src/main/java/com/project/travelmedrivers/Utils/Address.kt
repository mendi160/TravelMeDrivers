package com.project.travelmedrivers.Utils

class Address {
    var _city: String
        private set
        get() = field
    var _street: String
        private set
        get() = field
    var _number: Int
        private set
        get() = field

    constructor(_city: String, _street: String, _number: Int) {
        this._city = _city
        this._street = _street
        this._number = _number
    }

    override fun toString(): String {
        return "Address: $_city, $_street, $_number"
    }
}