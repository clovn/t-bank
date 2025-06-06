package com.example.tbank.domain.validation

import javax.inject.Inject

class ValidationManager @Inject constructor() {

    fun isValidName(name: String): Boolean {
        val regex = "^[а-яА-Яa-zA-Z]{3,20}$".toRegex()
        return regex.matches(name)
    }

    fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!?@#$%&*()]{8,}$".toRegex()
        return regex.matches(password)
    }

    fun isValidNumber(phoneNumber: String): Boolean {
        val regex = "^[78]\\d{10}$".toRegex()
        return regex.matches(phoneNumber)
    }

    fun arePasswordsMatching(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isValidTripName(tripName: String): Boolean {
        val regex = "^[a-zA-Zа-яА-Я0-9_-]{3,50}$".toRegex()
        return regex.matches(tripName)
    }

    fun isValidTripBudget(tripBudget: String): Boolean {
        val regex = "^(?!0\$)\\d+\$".toRegex()
        return regex.matches(tripBudget)
    }
}