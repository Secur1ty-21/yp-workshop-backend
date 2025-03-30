package ru.practicum.workshop.spend_analytic.feature.auth.data.entity

import jakarta.persistence.*
import lombok.ToString

@Entity
@ToString
@Table(name = "users")
class UserEntity public constructor() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0L
    @Column(name = "email")
    var email = ""
    @Column(name = "password")
    var password = ""

    constructor(id: Long = 0, email: String, password: String) : this() {
        this.id = id
        this.email = email
        this.password = password
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (id != other.id) return false
        if (email != other.email) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}