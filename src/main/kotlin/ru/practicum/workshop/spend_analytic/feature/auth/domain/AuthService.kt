package ru.practicum.workshop.spend_analytic.feature.auth.domain

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.UserAlreadyExistException
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.AuthException
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.RegisterRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.UserAuthRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.RefreshTokenResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.RegisterResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.UserAuthResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.security.JwtTokenProvider

@Service
class AuthService(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManager: AuthenticationManager,
) {
    fun register(registerRequest: RegisterRequest): RegisterResponse {
        if (userService.isUserEmailExist(registerRequest.email!!)) {
            throw UserAlreadyExistException("user with this email already exist")
        }
        val user = userService.createUser(registerRequest)
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.email, registerRequest.password)
        )
        val accessToken = jwtTokenProvider.createAccessToken(user.id, user.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(user.id, user.email)
        return RegisterResponse(userId = user.id, accessToken = accessToken, refreshToken =  refreshToken)
    }

    fun login(userAuthRequest: UserAuthRequest): UserAuthResponse {
        if (userService.isUserEmailExist(userAuthRequest.email!!).not()) {
            throw AuthException("Unauthorized")
        }
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userAuthRequest.email, userAuthRequest.password)
        )
        val user = userService.getUserByEmail(userAuthRequest.email)
        val accessToken = jwtTokenProvider.createAccessToken(user.id, user.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(user.id, user.email)
        return UserAuthResponse(userId = user.id, accessToken = accessToken, refreshToken = refreshToken)
    }

    fun refreshToken(refreshToken: String): RefreshTokenResponse {
        val jwtDto = jwtTokenProvider.refreshUserTokens(refreshToken)
        return RefreshTokenResponse(
            accessToken = jwtDto.accessToken,
            refreshToken = jwtDto.refreshToken
        )
    }

    fun isTokenValid(accessToken: String): Boolean {
        return jwtTokenProvider.isValid(accessToken)
    }
}