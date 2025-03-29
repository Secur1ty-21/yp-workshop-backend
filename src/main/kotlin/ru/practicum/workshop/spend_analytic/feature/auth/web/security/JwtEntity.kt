package ru.practicum.workshop.spend_analytic.feature.auth.web.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtEntity(
    val id: Long,
    private val email: String,
    private val password: String
) : UserDetails {
    override fun isEnabled() = true

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isAccountNonExpired() = true

    override fun getUsername() = email

    override fun getPassword() = password

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }
}