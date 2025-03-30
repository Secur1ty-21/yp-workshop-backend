package ru.practicum.workshop.spend_analytic.feature.auth.web.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class UserAuthResponse(
    @JsonProperty("user_id")
    val userId: Long,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)