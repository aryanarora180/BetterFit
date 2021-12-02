package com.example.betterfit.data

sealed class HomeState {

    object Loading : HomeState()

    data class Data(
        val trending: List<Competition>,
        val categories: List<String> = listOf("Walking", "Running", "Move minutes", "Heart points")
    ) : HomeState()

    class Error(val message: String) : HomeState()
}

sealed class LoginState {

    object Waiting : LoginState()

    object SigningIn : LoginState()

    class Error(
        val message: String
    ) : LoginState()

    object SignedIn : LoginState()
}