package com.example.rollercoastertycoon.service

import com.example.rollercoastertycoon.dto.MaintenanceDTO
import com.example.rollercoastertycoon.model.Maintenance
import com.example.rollercoastertycoon.repositories.MaintenanceRepository
import org.springframework.stereotype.Service

@Service
class MaintenanceService(
    private val maintenanceRepository: MaintenanceRepository
) {

    fun getAllMaintenances(): List<Maintenance> {
        return maintenanceRepository.findAll()
    }

    fun getMaintenancesByAttractionId(attractionId: Long): List<Maintenance> {
        return maintenanceRepository.findByAttractionId(attractionId)
    }

    fun addMaintenance(maintenanceDTO: MaintenanceDTO): Maintenance {
        val maintenance = Maintenance(
            attractionId = maintenanceDTO.attractionId,
            date = maintenanceDTO.date
        )
        return maintenanceRepository.save(maintenance)
    }


    fun deleteMaintenance(id: Long): Boolean {
        return if (maintenanceRepository.existsById(id)) {
            maintenanceRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}