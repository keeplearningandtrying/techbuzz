package com.sivalabs.quicklinks.repositories

import com.sivalabs.quicklinks.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(username: String): Optional<User>
}