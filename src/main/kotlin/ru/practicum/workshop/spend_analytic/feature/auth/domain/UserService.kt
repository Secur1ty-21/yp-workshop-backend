package ru.practicum.workshop.spend_analytic.feature.auth.domain

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.practicum.workshop.spend_analytic.feature.auth.data.UserRepository
import ru.practicum.workshop.spend_analytic.feature.auth.data.entity.UserEntity
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.DatabaseException
import ru.practicum.workshop.spend_analytic.feature.auth.web.model.request.RegisterRequest

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(registerRequest: RegisterRequest): UserEntity {
        val user = userRepository.createUser(UserEntity(email = registerRequest.email!!, password =  passwordEncoder.encode(registerRequest.password!!)))
            ?: throw DatabaseException("Db error in create user")
        return user
    }

    fun getUserById(id: Long): UserEntity {
        val user = userRepository.getUserById(id) ?: throw DatabaseException("Db error in get user by id")
        return user
    }

    fun getUserByEmail(email: String): UserEntity {
        val user = userRepository.getUserByEmail(email) ?: throw DatabaseException("Db error in get user by email")
        return user
    }

    fun isUserEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }
}