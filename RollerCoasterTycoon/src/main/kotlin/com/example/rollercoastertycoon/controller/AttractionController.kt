package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.model.Attraction
import com.example.rollercoastertycoon.service.AttractionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.rollercoastertycoon.dto.AttractionDTO


@RestController
@RequestMapping("/attractions")
class AttractionController(private val attractionService: AttractionService) {

    @PostMapping("/add")
    fun addAttraction(@RequestBody attractionDTO: AttractionDTO): ResponseEntity<Attraction> {
        val attraction = attractionService.addAttraction(attractionDTO)
        return ResponseEntity(attraction, HttpStatus.CREATED)
    }

    @GetMapping("/all")
    fun getAllAttractions(): ResponseEntity<List<Attraction>> {
        val attractions = attractionService.getAllAttractions()
        return ResponseEntity(attractions, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getAttractionById(@PathVariable id: Long): ResponseEntity<Attraction> {
        val attraction = attractionService.getAttractionById(id)
        return ResponseEntity(attraction, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateAttraction(@PathVariable id: Long, @RequestBody attractionDTO: AttractionDTO): ResponseEntity<Attraction> {
        val updatedAttraction = attractionService.updateAttraction(id, attractionDTO)
        return if (updatedAttraction != null) {
            ResponseEntity(updatedAttraction, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteAttraction(@PathVariable id: Long): ResponseEntity<Void> {
        attractionService.deleteAttraction(id)
        return ResponseEntity.noContent().build()
    }

}