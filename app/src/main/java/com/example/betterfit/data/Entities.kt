package com.example.betterfit.data

import com.squareup.moshi.Json
import java.io.Serializable

data class LoginRequestBody(
    @field:Json(name = "googleId") val idToken: String,
    @field:Json(name = "authCode") val authCode: String,
)

data class LoginResponse(
    @field:Json(name = "token") val token: String,
    @field:Json(name = "user_id") val userId: String,
)

data class Competition(
    @field:Json(name = "_id") val id: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "category") val category: String,
    @field:Json(name = "goal") val goal: Int,
    @field:Json(name = "entryFee") val entryFee: Int,
    @field:Json(name = "prizePool") val prizePool: Int,
    @field:Json(name = "startDate") val startDate: String,
    @field:Json(name = "endDate") val endDate: String,
) : Serializable

data class RegisterCompetitionResponse(
    @field:Json(name = "clientSecret") val clientSecret: String,
)

data class Registration(
    @field:Json(name = "progress") val progress: Int,
)
