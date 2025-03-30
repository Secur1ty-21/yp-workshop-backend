package ru.practicum.workshop.spend_analytic.feature.auth.data

import org.springframework.data.jpa.repository.JpaRepository
import ru.practicum.workshop.spend_analytic.feature.auth.data.entity.UserEntity
import java.util.*

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): Optional<UserEntity>
}