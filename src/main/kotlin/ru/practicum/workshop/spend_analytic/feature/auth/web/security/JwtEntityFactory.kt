package ru.practicum.workshop.spend_analytic.feature.auth.web.security

import ru.practicum.workshop.spend_analytic.feature.auth.data.entity.UserEntity

object JwtEntityFactory {
    fun create(userEntity: UserEntity): JwtEntity {
        return JwtEntity(
            id = userEntity.id,
            email = userEntity.email,
            password = userEntity.password
        )
    }
}