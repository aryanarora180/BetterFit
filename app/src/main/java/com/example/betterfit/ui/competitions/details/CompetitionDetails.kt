package com.example.betterfit.ui.competitions.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betterfit.data.Competition
import com.example.betterfit.data.CompetitionDetailsState
import com.example.betterfit.ui.CenteredView
import com.example.betterfit.ui.ServerConnectionError
import com.example.betterfit.ui.theme.BetterFitTheme

@Composable
fun CompetitionDetailsScreen(
    competitionId: String,
    viewModel: CompetitionDetailsViewModel = hiltViewModel(),
    onJoinCompetition: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getCompetitionDetails(competitionId)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val state by viewModel.state

            when (state) {
                is CompetitionDetailsState.Loading -> {
                    CenteredView {
                        CircularProgressIndicator()
                    }
                }

                is CompetitionDetailsState.Data -> {
                    CompetitionDetails(
                        competition = (state as CompetitionDetailsState.Data).details,
                        onJoinCompetition = {
                            viewModel.registerForCompetition()
                        }
                    )
                }

                is CompetitionDetailsState.Error -> {
                    CenteredView {
                        ServerConnectionError(
                            errorText = (state as CompetitionDetailsState.Error).message,
                            onRetryClick = { viewModel.getCompetitionDetails(competitionId) }
                        )
                    }
                }

                is CompetitionDetailsState.TakePayment -> {
                    onJoinCompetition((state as CompetitionDetailsState.TakePayment).clientSecret)
                }

                is CompetitionDetailsState.Registered -> {
                    TODO("Navigate to progress page")
                }
            }
        }
    }
}

@Composable
fun CompetitionDetails(
    competition: Competition,
    onJoinCompetition: () -> Unit
) {
    CompetitionHeader(
        modifier = Modifier.padding(top = 24.dp),
        competitionName = competition.title,
        competitionDuration = "${competition.startDate} to ${competition.endDate}"
    )

    if (competition.entryFee != 0) {
        PaidCompetitionDetails(
            modifier = Modifier.padding(vertical = 8.dp),
            currentPrizePool = competition.prizePool,
            entryFee = competition.entryFee
        )
    }
    CompetitionDetails(
        modifier = Modifier.padding(start = 24.dp, top = 8.dp, end = 24.dp),
        competitionDetails = """
                    • 5K to 10K and beyond - earn milestone badges and trophies for streaks and smashing personal bests
                    • Join or share public running challenges with your friends via any social media or messaging platform
                    • Beat running log bests and get a virtual high five from your worldwide run club
                    • Run in weekly and monthly NRC distance challenges or create a Challenge and invite your friends
                    • Reach goals, earn achievements and go the distance.
                """.trimIndent()
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onJoinCompetition,
        ) {
            Text(text = "Join")
        }
        if (competition.entryFee != 0) {
            Text(
                text = "You'll be redirected to a payment gateway to pay ₹${competition.entryFee}",
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp)
            )
        }
    }
}

@Composable
fun CompetitionHeader(
    modifier: Modifier = Modifier,
    competitionName: String,
    competitionDuration: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = competitionName,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
        Text(
            text = competitionDuration,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PaidCompetitionDetails(
    modifier: Modifier = Modifier,
    currentPrizePool: Int,
    entryFee: Int
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Prize pool: ₹$currentPrizePool",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Entry fee: ₹$entryFee",
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CompetitionDetails(
    modifier: Modifier = Modifier,
    competitionDetails: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = competitionDetails,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CompetitionDetailsScreenPreview() {
    BetterFitTheme {
        CompetitionDetailsScreen(
            competitionId = ""
        ) { }
    }
}