package ru.practicum.workshop.spend_analytic.feature.auth.web.model.response

import com.fasterxml.jackson.annotation.JsonProperty

class CheckResponse(
    @JsonProperty("is_valid")
    val refresh: Boolean,
    @JsonProperty("success")
    val success: Boolean = true
)