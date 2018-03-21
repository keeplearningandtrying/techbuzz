package com.sivalabs.quicklinks.services

import com.sivalabs.quicklinks.entities.User
import com.sivalabs.quicklinks.repositories.UserRepository
import com.sivalabs.quicklinks.web.models.SecurityUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class SecurityUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Optional<User> = userRepository.findByEmail(username)
        if(!user.isPresent) {
            throw UsernameNotFoundException("User email $username not found")
        }
        return SecurityUser(user.get())
    }
}