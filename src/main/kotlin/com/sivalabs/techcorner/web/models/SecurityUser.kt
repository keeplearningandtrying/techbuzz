package com.sivalabs.techcorner.web.models

import com.sivalabs.techcorner.entities.User
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser(val user: User) : UserDetails {

    override fun getUsername() = user.email

    override fun getPassword()= user.password

    override fun getAuthorities()= AuthorityUtils.createAuthorityList(user.role)

    override fun isEnabled() = true
    override fun isCredentialsNonExpired() = true
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
}