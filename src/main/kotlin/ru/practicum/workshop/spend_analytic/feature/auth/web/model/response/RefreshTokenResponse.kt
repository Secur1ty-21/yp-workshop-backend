package ru.practicum.workshop.spend_analytic.feature.auth.web.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class RefreshTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)