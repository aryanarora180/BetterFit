package com.example.betterfit.data

import android.content.Context
import com.example.betterfit.helper.DataStoreUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import javax.inject.Inject

object ApiClient {

    private const val BASE_URL = "https://betterfit-backend.herokuapp.com/api/"

    private lateinit var apiService: ApiService
    fun build(): ApiService {
        if (!(ApiClient::apiService.isInitialized)) {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
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

        @GET("competition/details/{competition_id}/")
        suspend fun getCompetitionDetails(
            @Path("competition_id") competitionId: String,
        ): Competition

        @POST("registration/register/{competition_id}/")
        suspend fun registerToCompetition(
            @Header("Authorization") bearerToken: String,
            @Path("competition_id") competitionId: String,
        ): RegisterCompetitionResponse
    }
}