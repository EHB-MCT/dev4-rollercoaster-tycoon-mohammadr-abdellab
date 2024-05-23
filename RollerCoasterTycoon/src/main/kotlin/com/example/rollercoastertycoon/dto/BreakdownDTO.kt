package com.example.rollercoastertycoon.dto

import java.time.LocalDate

data class BreakdownDTO(
    val id: Long = 0,
    val attractionId: Long,
    val dateReported: LocalDate,
    val dateResolved: LocalDate?,
    val description: String,
    val resolved: Boolean = false
)
