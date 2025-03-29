package ru.practicum.workshop.spend_analytic.feature.auth.web.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        var bearerToken = (request as HttpServletRequest).getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7)
        }
        runCatching {
            if (bearerToken != null && jwtTokenProvider.isValid(bearerToken)) {
                val authentication = jwtTokenProvider.getAuthentication(bearerToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }.onFailure { it.printStackTrace() }
        chain.doFilter(request, response)
    }
}