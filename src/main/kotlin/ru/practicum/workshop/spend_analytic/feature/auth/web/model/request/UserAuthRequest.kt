package ru.practicum.workshop.spend_analytic.feature.auth.web.model.request

import com.fasterxml.jackson.annotation.JsonProperty

class UserAuthRequest(
    @JsonProperty("email")
    val email: String?,
    @JsonProperty("password")
    val password: String?
)