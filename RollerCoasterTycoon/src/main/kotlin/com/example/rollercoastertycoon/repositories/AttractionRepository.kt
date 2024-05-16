package com.example.rollercoastertycoon.repositories

import com.example.rollercoastertycoon.model.Attraction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AttractionRepository : JpaRepository<Attraction, Long>



