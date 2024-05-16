package com.example.rollercoastertycoon.service
import com.example.rollercoastertycoon.repositories.UserRepository
import com.example.rollercoastertycoon.dto.UserDTO
import com.example.rollercoastertycoon.model.User
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun registerUser(userDTO: UserDTO): User {
        val user = User(
            username = userDTO.username,
            email = userDTO.email,
            password = userDTO.password,
        )
        return userRepository.save(user)
    }

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("user not found") }
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}
