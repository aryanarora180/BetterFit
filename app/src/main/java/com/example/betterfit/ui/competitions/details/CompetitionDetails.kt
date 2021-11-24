package com.example.betterfit.ui.competitions.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betterfit.ui.theme.BetterFitTheme

@Composable
fun CompetitionDetailsScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            CompetitionHeader(
                modifier = Modifier.padding(top = 24.dp),
                competitionName = "2500 steps a day",
                competitionDuration = "1 week"
            )
            PaidCompetitionDetails(
                modifier = Modifier.padding(vertical = 8.dp),
                currentPrizePool = 20_000,
                entryFee = 99
            )
            CompetitionDetails(
                modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 24.dp),
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
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Join")
                }
            }
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
        CompetitionDetailsScreen()
    }
}