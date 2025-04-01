package ru.practicum.workshop.spend_analytic.feature.auth.web.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.AuthException
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.DatabaseException
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.UserAlreadyExistException
import ru.practicum.workshop.spend_analytic.feature.auth.web.exception.ValidationException

@RestController
@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(e: ValidationException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(e: HttpMessageNotReadableException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleValidationError(e: UserAlreadyExistException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleValidationError(e: AuthException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleValidationError(e: DatabaseException): ResponseEntity<String> {
        return ResponseEntity<String>(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}