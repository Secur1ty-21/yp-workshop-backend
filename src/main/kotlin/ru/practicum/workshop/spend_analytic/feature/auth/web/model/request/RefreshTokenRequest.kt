package ru.practicum.workshop.spend_analytic.feature.auth.web.model.request

import com.fasterxml.jackson.annotation.JsonProperty

class RefreshTokenRequest(
    @JsonProperty("refresh_token")
    val refreshToken: String
)