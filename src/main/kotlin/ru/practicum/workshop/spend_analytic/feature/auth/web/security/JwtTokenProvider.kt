package ru.practicum.workshop.spend_analytic.feature.auth.web.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.practicum.workshop.spend_analytic.feature.auth.domain.UserService
import ru.practicum.workshop.spend_analytic.feature.auth.web.security.props.JwtProperties
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
    private val userService: UserService
) {
    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun createAccessToken(userId: Long, email: String): String {
        val claims = Jwts.claims()
            .subject(email)
            .add("id", userId)
            .build()
        val validity = Instant.now().plus(jwtProperties.access, ChronoUnit.MILLIS)
        return Jwts.builder()
            .claims(claims)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

    fun createRefreshToken(userId: Long, email: String): String {
        val claims = Jwts.claims()
            .subject(email)
            .add("id", userId)
            .build()
        val validity = Instant.now().plus(jwtProperties.refresh, ChronoUnit.MILLIS)
        return Jwts.builder()
            .claims(claims)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

    fun refreshUserTokens(refreshToken: String): JwtDto {
        if (!isValid(refreshToken)) {
            throw AccessDeniedException("")
        }
        val userId = getUserId(refreshToken).toLongOrNull() ?: throw IllegalArgumentException()
        val email = getEmail(refreshToken)
        return JwtDto(
            accessToken = createAccessToken(userId, email),
            refreshToken = createRefreshToken(userId, email)
        )
    }

    fun isValid(token: String): Boolean {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
        return claims.payload.expiration.after(Date())
    }

    fun getAuthentication(token: String): Authentication {
        val email = getEmail(token)
        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, "")
    }

    private fun getUserId(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload["id"].toString()
    }

    private fun getEmail(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload.subject
    }
}

class JwtDto(
    val accessToken: String,
    val refreshToken: String
)