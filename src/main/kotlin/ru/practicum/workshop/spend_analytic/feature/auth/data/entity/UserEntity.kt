package ru.practicum.workshop.spend_analytic.feature.auth.data.entity

data class UserEntity(
    val id: Long = 0L,
    val email: String,
    val password: String
)