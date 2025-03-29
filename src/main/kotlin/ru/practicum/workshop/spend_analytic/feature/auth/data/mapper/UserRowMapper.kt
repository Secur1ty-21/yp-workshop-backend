package ru.practicum.workshop.spend_analytic.feature.auth.data.mapper

import ru.practicum.workshop.spend_analytic.feature.auth.data.entity.UserEntity
import java.sql.ResultSet

object UserRowMapper {
    fun mapRow(resultSet: ResultSet): UserEntity? {
        if (resultSet.next()) {
            with(resultSet) {
                return UserEntity(
                    id = getLong("user_id"),
                    email = getString("user_email"),
                    password = getString("user_password")
                )
            }
        }
        return null
    }
}