package ru.practicum.workshop.spend_analytic.feature.auth.data

import org.springframework.stereotype.Repository
import ru.practicum.workshop.spend_analytic.feature.auth.data.entity.UserEntity
import ru.practicum.workshop.spend_analytic.feature.auth.data.mapper.UserRowMapper
import java.sql.PreparedStatement
import java.sql.ResultSet

@Repository
class UserRepository(
    private val dataSourceConfig: DataSourceConfig
) {
    fun createUser(userEntity: UserEntity): UserEntity? {
        runCatching {
            val connection = dataSourceConfig.getConnection()
            val statement = connection.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS).apply {
                setString(1, userEntity.email)
                setString(2, userEntity.password)
                executeUpdate()
            }
            runCatching {
                statement.generatedKeys.next()
                return userEntity.copy(id = statement.generatedKeys.getLong(1))
            }.onFailure { it.printStackTrace() }
        }.onFailure { it.printStackTrace() }
        return null
    }

    fun getUserById(id: Long): UserEntity? {
        runCatching {
            val connection = dataSourceConfig.getConnection()
            val statement = connection.prepareStatement(
                GET_USER_BY_ID,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            )
            statement.setLong(1, id)
            runCatching {
                val resultSet = statement.executeQuery()
                return UserRowMapper.mapRow(resultSet)
            }
        }
        return null
    }

    fun getUserByEmail(email: String): UserEntity? {
        runCatching {
            val connection = dataSourceConfig.getConnection()
            val statement = connection.prepareStatement(
                GET_USER_BY_EMAIL,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            )
            statement.setString(1, email)
            runCatching {
                val resultSet = statement.executeQuery()
                return UserRowMapper.mapRow(resultSet)
            }.onFailure { it.printStackTrace() }
        }.onFailure { it.printStackTrace() }
        return null
    }

    private companion object {
        const val INSERT_USER  = """
            INSERT INTO users (email, password)
            VALUES (?, ?)
        """
        const val GET_USER_BY_ID = """
            SELECT users.id as user_id,
            users.email as user_email,
            users.password as user_password
            FROM users
            WHERE users.id = ?
        """
        const val GET_USER_BY_EMAIL = """
            SELECT users.id as user_id,
            users.email as user_email,
            users.password as user_password
            FROM users
            WHERE users.email = ?
        """
    }
}