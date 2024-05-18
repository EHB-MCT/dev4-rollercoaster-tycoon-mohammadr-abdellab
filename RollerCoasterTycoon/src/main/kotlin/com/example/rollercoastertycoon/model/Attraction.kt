package com.example.rollercoastertycoon.model

import jakarta.persistence.*


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
    @ElementCollection
    @CollectionTable(name = "maintenance_dates", joinColumns = [JoinColumn(name = "attraction_id")])
    @Column(name = "maintenance_date")
    var maintenanceDates: List<String> = ArrayList(),
    var speed: Int,
    var heightRequirement: String?
 )
