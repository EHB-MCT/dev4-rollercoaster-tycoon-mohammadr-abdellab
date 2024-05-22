package com.example.rollercoastertycoon.controller

import com.example.rollercoastertycoon.dto.UserDTO
import com.example.rollercoastertycoon.model.Attraction
import com.example.rollercoastertycoon.model.User
import com.example.rollercoastertycoon.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("users")

class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): ResponseEntity<Any> {
        try {
            val user = userService.registerUser(userDTO)
        return ResponseEntity.ok(UserDTO(user.id, user.username, user.email, user.password, user.role))
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
    data class LoginRequest(
        val email: String,
        val password: String
    )
    @PostMapping("/login")
    fun loginUser(@RequestBody request: LoginRequest): ResponseEntity<User> {
        val user = userService.authenticateUser(request.email, request.password)
        return ResponseEntity.ok(user)
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

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        return if (userService.deleteUserById(id)) {
            ResponseEntity("User deleted successfully", HttpStatus.OK)
        } else {
            ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/{userId}/favorites/{attractionId}")
    fun addFavoriteAttraction(@PathVariable userId: Long, @PathVariable attractionId: Long): ResponseEntity<User> {
        val user = userService.addFavoriteAttraction(userId, attractionId)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/{userId}/favorites")
    fun getFavoriteAttractions(@PathVariable userId: Long): ResponseEntity<List<Attraction>> {
        val favorites = userService.getFavoriteAttractions(userId)
        return ResponseEntity.ok(favorites)
    }

    @DeleteMapping("/{userId}/favorites/{attractionId}")
    fun removeFavoriteAttraction(@PathVariable userId: Long, @PathVariable attractionId: Long): ResponseEntity<User> {
        val user = userService.removeFavoriteAttraction(userId, attractionId)
        return ResponseEntity.ok(user)
    }


}

