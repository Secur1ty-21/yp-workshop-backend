package ru.practicum.workshop.spend_analytic.feature.auth.web

object Validator {
    fun isEmailValid(email: String?): Boolean {
        if (email == null) return false
        if (email.contains("@").not()) return false
        val beforeDog = email.substringBefore("@")
        val afterDog = email.substringAfter("@")
        if (afterDog.contains(".").not()) return false
        return beforeDog.isNotBlank() &&
                afterDog.substringBefore(".").isNotBlank() &&
                afterDog.substringAfter(".").isNotBlank()
    }

    fun isLengthValid(text: String?, requiredLength: Int): Boolean {
        return text != null && text.length >= requiredLength
    }
}