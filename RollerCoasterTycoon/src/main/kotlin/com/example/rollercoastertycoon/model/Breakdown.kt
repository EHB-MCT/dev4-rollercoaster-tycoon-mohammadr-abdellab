package com.example.rollercoastertycoon.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "breakdowns")
data class Breakdown(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "attraction_id")
    var attraction: Attraction,
    var dateReported: LocalDate,
    var dateResolved: LocalDate?,
    var description: String,
    var resolved: Boolean = false
)
