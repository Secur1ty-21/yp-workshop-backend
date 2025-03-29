package ru.practicum.workshop.spend_analytic.feature.auth.web.security.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security.jwt")
class JwtProperties {
    var secret = ""
    var access = 0L
    var refresh = 0L
}