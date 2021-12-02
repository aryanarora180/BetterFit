package com.example.betterfit.ui.competitions.leaderboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betterfit.data.ProgressState
import com.example.betterfit.data.Registration
import com.example.betterfit.helper.parseAndFormatAsDate
import com.example.betterfit.ui.CenteredView
import com.example.betterfit.ui.ServerConnectionError
import com.example.betterfit.ui.competitions.details.CompetitionHeader

@Composable
fun CompetitionProgressScreen(
    viewModel: CompetitionProgressViewModel = hiltViewModel(),
    competitionId: String
) {
    LaunchedEffect(Unit) { viewModel.getProgress(competitionId) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val state by viewModel.state

            when (state) {
                is ProgressState.Loading -> {
                    CenteredView {
                        CircularProgressIndicator()
                    }
                }

                is ProgressState.Data -> {
                    val data = (state as ProgressState.Data)

                    CompetitionHeader(
                        modifier = Modifier.padding(top = 24.dp),
                        competitionName = data.competition.title,
                        competitionDuration = "${data.competition.startDate.parseAndFormatAsDate()} to ${data.competition.endDate.parseAndFormatAsDate()}"
                    )
                    ProgressList(participants = data.progress)
                }

                is ProgressState.Error -> {
                    CenteredView {
                        ServerConnectionError(
                            errorText = (state as ProgressState.Error).message,
                            onRetryClick = { viewModel.getProgress(competitionId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressList(
    participants: List<Registration>,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        items(participants) { participant ->
            ProgressCard("Anonymous", participant.progress)
        }
    }
}

@Composable
fun ProgressCard(
    name: String,
    score: Int
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Text(text = name, style = MaterialTheme.typography.subtitle1)
            Text(text = "Score: $score", style = MaterialTheme.typography.subtitle2)
        }
    }
}

