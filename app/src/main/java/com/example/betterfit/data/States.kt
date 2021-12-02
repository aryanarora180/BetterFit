package com.example.betterfit.data

sealed class HomeState {

    object Loading : HomeState()

    data class Data(
        val trending: List<Competition>,
        val categories: List<String> = listOf(
            "Walking",
            "Running",
            "Move minutes",
            "Heart points"
        ),
        val registered: List<Competition>
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

sealed class CompetitionDetailsState {

    object Loading : CompetitionDetailsState()
    data class TakePayment(
        val clientSecret: String
    ) : CompetitionDetailsState()

    object Registered : CompetitionDetailsState()

    data class Data(
        val details: Competition,
    ) : CompetitionDetailsState()

    class Error(val message: String) : CompetitionDetailsState()
}

sealed class ProgressState {

    object Loading : ProgressState()

    data class Data(
        val competition: Competition,
        val progress: List<Registration>
    ) : ProgressState()

    class Error(val message: String) : ProgressState()
}
