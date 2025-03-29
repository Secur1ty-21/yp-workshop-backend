package ru.practicum.workshop.spend_analytic.feature.auth.web.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.practicum.workshop.spend_analytic.feature.auth.domain.UserService

@Service
class JwtUserDetailsService(
    private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.getUserByEmail(username ?: "")
        return JwtEntityFactory.create(user)
    }
}