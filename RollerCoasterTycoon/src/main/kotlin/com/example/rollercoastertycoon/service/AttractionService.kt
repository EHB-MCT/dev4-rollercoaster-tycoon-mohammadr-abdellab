package com.example.rollercoastertycoon.service
import com.example.rollercoastertycoon.repositories.AttractionRepository
import com.example.rollercoastertycoon.dto.AttractionDTO
import com.example.rollercoastertycoon.model.Attraction
import com.example.rollercoastertycoon.model.Category
import com.example.rollercoastertycoon.repositories.CategoryRepository
import org.springframework.stereotype.Service

@Service
class AttractionService(private val attractionRepository: AttractionRepository, private val categoryRepository: CategoryRepository) {

    fun getAllAttractions(): List<Attraction> {
        return attractionRepository.findAll()
    }

    fun getAttractionById(id: Long): Attraction {
        return attractionRepository.findById(id).orElseThrow { NoSuchElementException("Attraction not found") }
    }

    fun addAttraction(attractionDTO: AttractionDTO): Attraction {
        val category = categoryRepository.findByName(attractionDTO.category)
            ?: throw IllegalArgumentException("Category not found: ${attractionDTO.category}")
        val attraction = Attraction(
            name = attractionDTO.name,
            category = category,
            capacity = attractionDTO.capacity,
            yearBuilt = attractionDTO.yearBuilt,
            image = attractionDTO.image,
            onRideVideo = attractionDTO.onRideVideo,
            operational = attractionDTO.operational,
            maintenanceDates = attractionDTO.maintenanceDates,
            speed = attractionDTO.speed,
            heightRequirement = attractionDTO.heightRequirement,
            breakdownCount = attractionDTO.breakdownCount,

        )
        return attractionRepository.save(attraction)
    }

    fun updateAttraction(id: Long, attractionDTO: AttractionDTO): Attraction? {
        val existingAttraction = attractionRepository.findById(id)
        if (existingAttraction.isPresent) {
            val attraction = existingAttraction.get()
            val category = categoryRepository.findByName(attractionDTO.category)
                ?: throw IllegalArgumentException("Category not found: ${attractionDTO.category}")
            attraction.name = attractionDTO.name
            attraction.category = category
            attraction.capacity = attractionDTO.capacity
            attraction.yearBuilt = attractionDTO.yearBuilt
            attraction.image = attractionDTO.image
            attraction.onRideVideo = attractionDTO.onRideVideo
            attraction.operational = attractionDTO.operational
            attraction.maintenanceDates = attractionDTO.maintenanceDates
            attraction.speed = attractionDTO.speed
            attraction.heightRequirement = attractionDTO.heightRequirement
            attraction.breakdownCount = attractionDTO.breakdownCount

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

    fun rateAttraction(id: Long, rating: Double): Attraction? {
        val existingAttraction = attractionRepository.findById(id)
        if (existingAttraction.isPresent) {
            val attraction = existingAttraction.get()
            attraction.rating = rating
            return attractionRepository.save(attraction)
        }
        return null
    }

}