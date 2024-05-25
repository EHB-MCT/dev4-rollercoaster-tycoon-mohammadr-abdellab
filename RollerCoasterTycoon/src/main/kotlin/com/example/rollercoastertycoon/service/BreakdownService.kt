package com.example.rollercoastertycoon.service

import com.example.rollercoastertycoon.dto.BreakdownDTO
import com.example.rollercoastertycoon.model.Breakdown
import com.example.rollercoastertycoon.repositories.AttractionRepository
import com.example.rollercoastertycoon.repositories.BreakdownRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BreakdownService(
    private val breakdownRepository: BreakdownRepository,
    private val attractionRepository: AttractionRepository
) {

    fun getAllBreakdowns(): List<Breakdown> {
        return breakdownRepository.findAll()
    }

    fun getBreakdownsByAttraction(attractionId: Long): List<Breakdown> {
        return breakdownRepository.findAllByAttractionId(attractionId)
    }

    fun getBreakdownById(id: Long): Breakdown {
        return breakdownRepository.findById(id).orElseThrow { NoSuchElementException("Breakdown not found") }
    }

    fun addBreakdown(breakdownDTO: BreakdownDTO): Breakdown {
        val attraction = attractionRepository.findById(breakdownDTO.attractionId)
            .orElseThrow { NoSuchElementException("Attraction not found") }

        val breakdown = Breakdown(
            attraction = attraction,
            dateReported = breakdownDTO.dateReported,
            dateResolved = breakdownDTO.dateResolved,
            description = breakdownDTO.description,
            resolved = breakdownDTO.resolved
        )

        attraction.breakdownCount += 1
        attraction.operational = false
        attractionRepository.save(attraction)
        return breakdownRepository.save(breakdown)
    }

    fun updateBreakdown(id: Long, breakdownDTO: BreakdownDTO): Breakdown? {
        val existingBreakdown = breakdownRepository.findById(id)
        if (existingBreakdown.isPresent) {
            val breakdown = existingBreakdown.get()
            val attraction = attractionRepository.findById(breakdownDTO.attractionId)
                .orElseThrow { NoSuchElementException("Attraction not found") }

            breakdown.dateReported = breakdownDTO.dateReported
            breakdown.dateResolved = breakdownDTO.dateResolved
            breakdown.description = breakdownDTO.description
            breakdown.resolved = breakdownDTO.resolved
            breakdown.attraction = attraction
            return breakdownRepository.save(breakdown)
        }
        return null
    }

    fun resolveBreakdown(id: Long): Breakdown {
        val breakdown = breakdownRepository.findById(id).orElseThrow { NoSuchElementException("Breakdown not found") }
        breakdown.resolved = true
        breakdown.dateResolved = LocalDate.now()
        return breakdownRepository.save(breakdown)
    }

    fun deleteBreakdown(id: Long): Boolean {
        return if (breakdownRepository.existsById(id)) {
            val breakdown = getBreakdownById(id)
            val attraction = breakdown.attraction
            attraction.breakdownCount -= 1
            breakdownRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
