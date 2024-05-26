package com.example.rollercoastertycoon.model

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "attractions")
 class Attraction(
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
   var name: String,
   @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category,
   var capacity: Int,
   var yearBuilt: Int,
   var image: String,
   var onRideVideo: String?,
   var operational: Boolean,
   @OneToMany(mappedBy = "attractionId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
   var maintenanceDates: List<Maintenance> = mutableListOf(),
   var speed: Int,
   var heightRequirement: String?,
   var breakdownCount: Int = 0

)
