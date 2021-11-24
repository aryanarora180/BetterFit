package com.example.betterfit.ui.competitions.progress

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betterfit.data.Competition
import com.example.betterfit.ui.competitions.details.CompetitionHeader
import com.example.betterfit.ui.theme.BetterFitTheme

@Composable
fun ProgressScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CompetitionHeader(
                modifier = Modifier.padding(top = 24.dp),
                competitionName = "2500 steps a day",
                competitionDuration = "1 week"
            )
            PaidCompetitionDetails(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Categories(
                isLoading = false,
                participants = listOf(
                    Pair("Rahul Vegesna", "3500"),
                    Pair("Rahul Vegesna", "2500")
                )
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "You're registered",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Categories(
    isLoading: Boolean = true,
    participants: List<Pair<String, String>>? = null,
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(start = 24.dp, top = 4.dp)
        )
    } else {
        LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
            items(participants ?: emptyList()) { participant ->
                CategoryCard(participant.first, participant.second)
            }
        }
    }
}

@Composable
fun CategoryCard(
    CompetitorName: String,
    CompetitorScore: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Text(text = CompetitorName, style = MaterialTheme.typography.subtitle1)
            Text(text = CompetitorScore, style = MaterialTheme.typography.subtitle2)
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProgressScreenPreview() {
    BetterFitTheme {
        ProgressScreen()
    }
}