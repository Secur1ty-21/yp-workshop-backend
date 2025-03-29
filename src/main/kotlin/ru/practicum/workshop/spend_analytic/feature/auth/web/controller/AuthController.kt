package ru.practicum.workshop.spend_analytic.feature.auth.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.practicum.workshop.spend_analytic.feature.auth.domain.AuthService
import ru.practicum.workshop.spend_analytic.feature.auth.web.Validator
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.ValidationException
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.CheckRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.RefreshTokenRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.RegisterRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.UserAuthRequest
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.CheckResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.RefreshTokenResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.RegisterResponse
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.response.UserAuthResponse

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/registration")
    fun registerUser(@RequestBody registerRequest: RegisterRequest): ResponseEntity<RegisterResponse> {
        checkEmailAndPassword(registerRequest.email, registerRequest.password)
        val registerResponse = authService.register(registerRequest)
        return ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun userAuth(@RequestBody userAuthRequest: UserAuthRequest): UserAuthResponse {
        checkEmailAndPassword(userAuthRequest.email, userAuthRequest.password)
        return authService.login(userAuthRequest)
    }

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        return authService.refreshToken(refreshTokenRequest.refreshToken)
    }

    @GetMapping("/check")
    fun isValid(@RequestBody checkRequest: CheckRequest): CheckResponse {
        return CheckResponse(authService.isTokenValid(checkRequest.accessToken))
    }

    @PostMapping("/recovery")
    fun recoveryUserPassword() {
        TODO()
    }

    private fun checkEmailAndPassword(email: String?, password: String?) {
        if (Validator.isEmailValid(email).not()) {
            throw ValidationException("Incorrect email")
        }
        if (Validator.isLengthValid(password, 6).not()) {
            throw ValidationException("Password should be more than 6 symbols")
        }
    }
}