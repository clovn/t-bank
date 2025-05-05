package com.example.tbank.domain.validation

class ValidationManager {

    fun isValidLogin(login: String): Boolean {
        val regex = "^[a-zA-Z0-9_-]{3,20}$".toRegex()
        return regex.matches(login)
    }

    fun isValidName(name: String): Boolean {
        val regex = "^[а-яА-Я]{3,20}$".toRegex()
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
}