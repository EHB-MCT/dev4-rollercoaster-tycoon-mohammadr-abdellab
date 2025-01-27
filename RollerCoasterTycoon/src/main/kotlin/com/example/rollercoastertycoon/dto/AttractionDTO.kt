package com.example.rollercoastertycoon.dto

import com.example.rollercoastertycoon.model.Maintenance
import java.time.LocalDate

data class AttractionDTO(
    var id: Long = 0,
    var name: String,
    var category: String,
    var capacity: Int,
    var yearBuilt: Int,
    var image: String,
    var onRideVideo: String?,
    var operational: Boolean,
    var maintenanceDates: List<LocalDate>? = null,
    var speed: Int,
    var heightRequirement: String?,
    var breakdownCount: Int = 0
)