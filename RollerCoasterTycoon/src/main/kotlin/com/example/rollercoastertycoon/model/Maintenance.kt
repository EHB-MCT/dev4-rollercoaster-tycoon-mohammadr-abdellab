package com.example.rollercoastertycoon.model

import java.time.LocalDate
import jakarta.persistence.*

@Entity
@Table(name = "maintenance")
data class Maintenance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "attraction_id")
    val attractionId: Long,

    @Column(name = "date")
    val date: LocalDate
)



