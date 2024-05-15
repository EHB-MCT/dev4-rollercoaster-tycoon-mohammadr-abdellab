package com.example.rollercoastertycoon.model

// Attraction.kt
data class Attraction(
    val id: Double,
    val name: String,
    val category: String,
    val capacity: Int,
    val yearBuilt: Int,
    val image: String,
    val onRideVideo: String?,
    val operational: Boolean,
    val maintenanceDates: List<String>, // Change this to a Date type if needed
    val additionalAttributes: Map<String, Any> // Additional attributes as key-value pairs
)
