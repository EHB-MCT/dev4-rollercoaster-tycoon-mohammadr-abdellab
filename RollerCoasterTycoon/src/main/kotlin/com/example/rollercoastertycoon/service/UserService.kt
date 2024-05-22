package com.example.rollercoastertycoon.service
import com.example.rollercoastertycoon.repositories.UserRepository
import com.example.rollercoastertycoon.dto.UserDTO
import com.example.rollercoastertycoon.model.Attraction
import com.example.rollercoastertycoon.model.User
import com.example.rollercoastertycoon.repositories.AttractionRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository,
    private val attractionRepository: AttractionRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun registerUser(userDTO: UserDTO): User {
        val existingUser = userRepository.findByEmail(userDTO.email)
        if (existingUser != null) {
            throw IllegalArgumentException("Email ${userDTO.email} is already in use")
        }
        val hashedPassword = passwordEncoder.encode(userDTO.password)
        val user = User(
            username = userDTO.username,
            email = userDTO.email,
            password = hashedPassword,
            role =  userDTO.role,
        )
        return userRepository.save(user)
    }
    fun authenticateUser(email: String, password: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw NoSuchElementException("User not found with email: $email")

        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        return user
    }




    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("user not found") }
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun deleteUserById(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }


    fun addFavoriteAttraction(userId: Long, attractionId: Long): User {
        val user = getUserById(userId) ?: throw NoSuchElementException("User not found")
        val attraction = attractionRepository.findById(attractionId).orElseThrow { NoSuchElementException("Attraction not found") }
        if (user.favorites.contains(attraction)) {
            throw IllegalArgumentException("Attraction is already in the favorites list")
        }

        user.favorites.add(attraction)
        return userRepository.save(user)

    }

    fun getFavoriteAttractions(userId: Long): List<Attraction> {
        val user = getUserById(userId) ?: throw NoSuchElementException("User not found")
        return user.favorites
    }

    fun removeFavoriteAttraction(userId: Long, attractionId: Long): User {
        val user = getUserById(userId) ?: throw NoSuchElementException("User not found")
        val attraction = attractionRepository.findById(attractionId).orElseThrow { NoSuchElementException("Attraction not found") }

        if (!user.favorites.contains(attraction)) {
            throw IllegalArgumentException("Attraction is not in the favorites list")
        }

        user.favorites.remove(attraction)
        return userRepository.save(user)
    }
}
