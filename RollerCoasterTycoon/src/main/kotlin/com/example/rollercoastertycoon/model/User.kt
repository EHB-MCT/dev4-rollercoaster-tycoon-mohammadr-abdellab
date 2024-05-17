package com.example.rollercoastertycoon.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    var role: String,
    @ManyToMany
    @JoinTable(
    name = "user_favorites",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "attraction_id")]
    )
    val favorites: MutableList<Attraction> = mutableListOf()
)
