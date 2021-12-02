package com.example.betterfit.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object ApiClient {

    private const val BASE_URL = "https://betterfit-backend.herokuapp.com/api/"

    private lateinit var apiService: ApiService
    fun build(): ApiService {
        if (!(ApiClient::apiService.isInitialized)) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    interface ApiService {

        @POST("user/login/")
        suspend fun signIn(
            @Body loginBody: LoginRequestBody,
        ): LoginResponse

        @GET("competition/trending/")
        suspend fun getTrendingCompetitions(): List<Competition>

        @GET("competition/details/{competition_id}")
        suspend fun getCompetitionDetails(
            @Path("competition_id") competitionId: String,
        ): Competition

        @POST("registration/register/{user_id}/{competition_id}/")
        suspend fun registerToCompetition(
            @Path("user_id") userId: String,
            @Path("competition_id") competitionId: String,
        ): RegisterCompetitionResponse
    }
}