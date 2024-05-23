package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.dto.BreakdownDTO
import com.example.rollercoastertycoon.model.Breakdown
import com.example.rollercoastertycoon.service.BreakdownService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/breakdowns")
class BreakdownController(private val breakdownService: BreakdownService) {

    @GetMapping
    fun getAllBreakdowns(): ResponseEntity<List<Breakdown>> {
        return ResponseEntity.ok(breakdownService.getAllBreakdowns())
    }

    @GetMapping("/attraction/{attractionId}")
    fun getBreakdownsByAttraction(@PathVariable attractionId: Long): ResponseEntity<List<Breakdown>> {
        return ResponseEntity.ok(breakdownService.getBreakdownsByAttraction(attractionId))
    }

    @GetMapping("/{id}")
    fun getBreakdownById(@PathVariable id: Long): ResponseEntity<Breakdown> {
        return ResponseEntity.ok(breakdownService.getBreakdownById(id))
    }

    @PostMapping
    fun addBreakdown(@RequestBody breakdownDTO: BreakdownDTO): ResponseEntity<Breakdown> {
        return ResponseEntity.ok(breakdownService.addBreakdown(breakdownDTO))
    }

    @PutMapping("/{id}")
    fun updateBreakdown(@PathVariable id: Long, @RequestBody breakdownDTO: BreakdownDTO): ResponseEntity<Breakdown?> {
        return ResponseEntity.ok(breakdownService.updateBreakdown(id, breakdownDTO))
    }

    @PutMapping("/resolve/{id}")
    fun resolveBreakdown(@PathVariable id: Long): ResponseEntity<Breakdown> {
        return ResponseEntity.ok(breakdownService.resolveBreakdown(id))
    }

    @DeleteMapping("/{id}")
    fun deleteBreakdown(@PathVariable id: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(breakdownService.deleteBreakdown(id))
    }
}
