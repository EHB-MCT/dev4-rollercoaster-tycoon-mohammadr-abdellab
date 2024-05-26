package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.dto.MaintenanceDTO
import com.example.rollercoastertycoon.model.Maintenance
import com.example.rollercoastertycoon.service.AttractionService
import com.example.rollercoastertycoon.service.MaintenanceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin
@RestController
@RequestMapping("/maintenance")
class MaintenanceController(
    private val maintenanceService: MaintenanceService,
    private val attractionService: AttractionService
) {

    @GetMapping("/all")
    fun getAllMaintenances(): ResponseEntity<List<Maintenance>> {
        val maintenances = maintenanceService.getAllMaintenances()
        return ResponseEntity(maintenances, HttpStatus.OK)
    }

    @GetMapping("/attraction/{attractionId}")
    fun getMaintenancesByAttractionId(@PathVariable attractionId: Long): ResponseEntity<List<Maintenance>> {
        val maintenances = maintenanceService.getMaintenancesByAttractionId(attractionId)
        return ResponseEntity(maintenances, HttpStatus.OK)
    }

    @PostMapping("/add")
    fun addMaintenance(@RequestBody maintenanceDTO: MaintenanceDTO): ResponseEntity<Maintenance> {
        val maintenance = maintenanceService.addMaintenance(maintenanceDTO)
        return ResponseEntity(maintenance, HttpStatus.CREATED)
    }


    @DeleteMapping("/delete/{id}")
    fun deleteMaintenance(@PathVariable id: Long): ResponseEntity<Any> {
        return if (maintenanceService.deleteMaintenance(id)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}