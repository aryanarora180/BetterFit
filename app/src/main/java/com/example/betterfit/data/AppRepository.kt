package com.example.betterfit.data

import android.util.Log
import com.example.betterfit.helper.DataStoreUtils
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class AppRepository {

    private val apiClient = ApiClient.build()

    suspend fun signIn(idToken: String, authCode: String): OperationResult<LoginResponse> {
        return try {
            val result = apiClient.signIn(LoginRequestBody(idToken, authCode))
            OperationResult.Success(result)
        } catch (e: HttpException) {
            getParsedErrorBody(e.code(), e.response()?.errorBody()?.string())
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    suspend fun getCompetitionDetails(competitionId: String): OperationResult<Competition> {
        return try {
            val result = apiClient.getCompetitionDetails(competitionId)
            OperationResult.Success(result)
        } catch (e: HttpException) {
            OperationResult.Error(
                e.response()?.code() ?: 500,
                e.response()?.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    suspend fun getHomeContent(authToken: String): OperationResult<Pair<List<Competition>, List<Competition>>> {
        return try {
            val trending = apiClient.getTrendingCompetitions()
            val registered = apiClient.getRegisteredCompetitions("Bearer $authToken")
            OperationResult.Success(Pair(trending, registered))
        } catch (e: HttpException) {
            OperationResult.Error(
                e.response()?.code() ?: 500,
                e.response()?.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    suspend fun registerToCompetition(
        competitionId: String,
        authToken: String?
    ): OperationResult<RegisterCompetitionResponse> {
        return try {
            val result =
                apiClient.registerToCompetition(
                    "Bearer $authToken",
                    competitionId
                )
            OperationResult.Success(result)
        } catch (e: HttpException) {
            OperationResult.Error(
                e.response()?.code() ?: 500,
                e.response()?.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    suspend fun getLeaderboard(competitionId: String): OperationResult<Pair<Competition, List<Registration>>> {
        return try {
            OperationResult.Success(
                Pair(
                    apiClient.getCompetitionDetails(competitionId),
                    apiClient.getLeaderboard(competitionId)
                )
            )
        } catch (e: HttpException) {
            OperationResult.Error(
                e.response()?.code() ?: 500,
                e.response()?.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    private fun getParsedErrorBody(status: Int, errorBody: String?): OperationResult.Error {
        return if (errorBody != null) {
            try {
                val jsonObject = JSONObject(errorBody)
                OperationResult.Error(
                    status,
                    jsonObject.getJSONObject("error").getString("message")
                )
            } catch (e: Exception) {
                OperationResult.Error(status, null)
            }
        } else {
            OperationResult.Error(status, null)
        }
    }

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Unknown error occurred"
    }
}