package ru.practicum.workshop.spend_analytic.feature.auth.web.model.request

import com.fasterxml.jackson.annotation.JsonProperty

class CheckRequest(
    @JsonProperty("access_token")
    val accessToken: String
)