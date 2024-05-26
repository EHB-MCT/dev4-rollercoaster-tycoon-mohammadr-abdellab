package com.example.rollercoastertycoon.dto

import java.time.LocalDate

data class MaintenanceDTO(
    var id: Long = 0,
    var attractionId: Long,
    var date: LocalDate
)
