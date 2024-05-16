package com.example.rollercoastertycoon.repositories

import com.example.rollercoastertycoon.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>

