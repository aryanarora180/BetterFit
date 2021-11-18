package com.example.betterfit.data

sealed class OperationResult<out T> {
    data class Success<T>(val data: T) : OperationResult<T>()
    data class Error(val status: Int, val message: String?) : OperationResult<Nothing>() {
        fun getErrorMessage() = message ?: getErrorMessage(status)
    }

    companion object {

        fun getErrorMessage(errorCode: Int): String {
            return when (errorCode) {
                400 -> "Unexplained bad request"
                401 -> "Your password has changed. Please sign in again"
                413 -> "File size too large"
                422 -> "Invalid details"
                500 -> "A server error occurred. Please try again later"
                502 -> "Bad gateway"
                else -> "Unable to connect to our servers"
            }
        }
    }
}