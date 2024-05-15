package com.example.rollercoastertycoon.model

data class Attraction(
    val id: Double,
    val name: String,
    val category: String,
    val capacity: Int,
    val yearBuilt: Int,
    val image: String,
    val onRideVideo: String?,
    val operational: Boolean,
    val maintenanceDates: List<String>,
    val additionalAttributes: Map<String, Any>
)
