package ru.practicum.workshop.spend_analytic.feature.auth.web.model.response

class RegisterResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)