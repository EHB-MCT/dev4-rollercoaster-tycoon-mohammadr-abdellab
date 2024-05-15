package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.model.Attraction
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/attractions")
class AttractionController {
    private val attractions = mutableListOf<Attraction>()

    @PostMapping("/add")
    fun addAttraction(@RequestBody attraction: Attraction): String {
        attractions.add(attraction)
        return "Attraction added: $attraction"
    }

    @GetMapping("/all")
    fun getAllAttractions(): List<Attraction> {
        if (attractions.isEmpty()) {
            val rollerCoaster = Attraction(
                    id = 1.0,
                    name = "Roller Coaster",
                    category = "Thrill",
                    capacity = 100,
                    yearBuilt = 2022,
                    image = "https://example.com/rollercoaster.jpg",
                    onRideVideo = "https://youtube.com/rollercoaster",
                    operational = true,
                    maintenanceDates = listOf("2023-05-10", "2024-01-15"),
                    additionalAttributes = mapOf("heightRequirement" to 120, "fastPass" to true)
            )
            attractions.add(rollerCoaster)
        }
        return attractions
    }
}