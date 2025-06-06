package com.example.tbank.data.model

data class LoginResponse(
    val userDto: UserDto,
    val jwtTokenPairDto: JwtTokens
)

data class JwtTokens(
    val accessToken: String,
    val refreshToken: String
)

data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)
