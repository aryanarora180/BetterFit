package com.example.betterfit.data

import com.squareup.moshi.Json

data class DataResponse<T>(
    @field:Json(name = "error") val errorOccurred: Boolean,
    @field:Json(name = "message") val errorMessage: String,
    @field:Json(name = "data") val data: T,
)

data class NoDataResponse(
    @field:Json(name = "error") val errorOccurred: Boolean,
    @field:Json(name = "message") val errorMessage: String,
)

data class Competition(
    val competitionName: String,
    val competitionDuration: String,
    val competitionRegistered: Int
)

