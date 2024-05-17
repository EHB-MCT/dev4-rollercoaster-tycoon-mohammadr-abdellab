package com.example.rollercoastertycoon.dto

data class UserDTO(
    var id: Long = 0,
    var username: String,
    var email: String,
    var password: String,
    var role: String,
    var favorites: List<Long> = listOf()
    )

