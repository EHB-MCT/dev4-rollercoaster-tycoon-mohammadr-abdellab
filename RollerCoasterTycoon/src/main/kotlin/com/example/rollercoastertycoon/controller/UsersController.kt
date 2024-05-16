package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.dto.UserDTO
import com.example.rollercoastertycoon.model.User
import com.example.rollercoastertycoon.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("users")

class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userService.registerUser(userDTO)
        return ResponseEntity.ok(UserDTO(user.id, user.username, user.email, user.password))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return ResponseEntity(user, HttpStatus.OK)

    }

    @GetMapping("/all")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

}

