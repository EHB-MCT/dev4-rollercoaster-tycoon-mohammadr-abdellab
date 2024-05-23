package com.example.rollercoastertycoon.repositories

import com.example.rollercoastertycoon.model.Breakdown
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BreakdownRepository : JpaRepository<Breakdown, Long> {
    fun findAllByAttractionId(attractionId: Long): List<Breakdown>
}
