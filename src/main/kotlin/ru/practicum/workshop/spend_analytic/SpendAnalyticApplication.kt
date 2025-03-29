package ru.practicum.workshop.spend_analytic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class SpendAnalyticApplication

fun main(args: Array<String>) {
	runApplication<SpendAnalyticApplication>(*args)
}
