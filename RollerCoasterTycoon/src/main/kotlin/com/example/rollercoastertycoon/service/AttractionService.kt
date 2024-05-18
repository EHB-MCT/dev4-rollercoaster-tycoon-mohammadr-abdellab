package com.example.rollercoastertycoon.service
import com.example.rollercoastertycoon.repositories.AttractionRepository
import com.example.rollercoastertycoon.dto.AttractionDTO
import com.example.rollercoastertycoon.model.Attraction
import org.springframework.stereotype.Service

@Service
class AttractionService(private val attractionRepository: AttractionRepository) {

    fun getAllAttractions(): List<Attraction> {
        return attractionRepository.findAll()
    }

    fun getAttractionById(id: Long): Attraction {
        return attractionRepository.findById(id).orElseThrow { NoSuchElementException("Attraction not found") }
    }

    fun addAttraction(attractionDTO: AttractionDTO): Attraction {
        val attraction = Attraction(
            name = attractionDTO.name,
            category = attractionDTO.category,
            capacity = attractionDTO.capacity,
            yearBuilt = attractionDTO.yearBuilt,
            image = attractionDTO.image,
            onRideVideo = attractionDTO.onRideVideo,
            operational = attractionDTO.operational,
            maintenanceDates = attractionDTO.maintenanceDates,
            speed = attractionDTO.speed,
            heightRequirement = attractionDTO.heightRequirement
        )
        return attractionRepository.save(attraction)
    }

    fun updateAttraction(id: Long, attractionDTO: AttractionDTO): Attraction? {
        val existingAttraction = attractionRepository.findById(id)
        if (existingAttraction.isPresent) {
            val attraction = existingAttraction.get()
            attraction.name = attractionDTO.name
            attraction.category = attractionDTO.category
            attraction.capacity = attractionDTO.capacity
            attraction.yearBuilt = attractionDTO.yearBuilt
            attraction.image = attractionDTO.image
            attraction.onRideVideo = attractionDTO.onRideVideo
            attraction.operational = attractionDTO.operational
            attraction.maintenanceDates = attractionDTO.maintenanceDates
            attraction.speed = attractionDTO.speed
            attraction.heightRequirement = attractionDTO.heightRequirement
            return attractionRepository.save(attraction)
        }
        return null
    }

    fun deleteAttraction(id: Long): Boolean {
            return if (attractionRepository.existsById(id)) {
                attractionRepository.deleteById(id)
                true
            } else {
                false
            }
    }
}