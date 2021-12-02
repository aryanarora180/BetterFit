package com.example.betterfit.data

import org.json.JSONObject
import retrofit2.HttpException

class AppRepository {

    private val apiClient = ApiClient.build()

    suspend fun signIn(authCode: String): OperationResult<LoginResponse> {
        return try {
            val result = apiClient.signIn(LoginRequestBody(authCode))
            OperationResult.Success(result)
        } catch (e: HttpException) {
            e.printStackTrace()
            getParsedErrorBody(e.code(), e.response()?.errorBody()?.string())
        } catch (e: Exception) {
            e.printStackTrace()
            OperationResult.Error(1000, OperationResult.getErrorMessage(1000))
        }
    }

    suspend fun getTrendingCompetitions(): OperationResult<List<Competition>> {
        return try {
            val result = apiClient.getTrendingCompetitions()
            OperationResult.Success(result)
        } catch (e: HttpException) {
            OperationResult.Error(
                e.response()?.code() ?: 500,
                e.response()?.errorBody()?.string() ?: "No error message"
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
}