package ru.practicum.workshop.spend_analytic.feature.auth.data

import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.Connection
import javax.sql.DataSource

@Configuration
class DataSourceConfig(private val dataSource: DataSource) {
    fun getConnection(): Connection {
        return DataSourceUtils.getConnection(dataSource)
    }
}