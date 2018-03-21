package com.sivalabs.techcorner.repositories

import com.sivalabs.techcorner.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(username: String): Optional<User>
}