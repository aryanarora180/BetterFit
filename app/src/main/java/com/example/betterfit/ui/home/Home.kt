package com.example.betterfit.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betterfit.data.Competition
import com.example.betterfit.data.HomeState
import com.example.betterfit.helper.parseAndFormatAsDate
import com.example.betterfit.ui.CenteredView
import com.example.betterfit.ui.ServerConnectionError
import com.example.betterfit.ui.theme.BetterFitTheme
import java.util.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onTrendingTap: (String) -> Unit,
    onRegisteredTap: (String) -> Unit
) {
    LaunchedEffect(Unit) { viewModel.getTrendingCompetitions() }

    val state by viewModel.state

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                text = "BetterFit", style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 8.dp)
            )

            when (state) {
                is HomeState.Loading -> {
                    CenteredView {
                        CircularProgressIndicator()
                    }
                }

                is HomeState.Data -> {
                    TrendingCompetitions(
                        competitions = (state as HomeState.Data).trending,
                        onCompetitionTap = onTrendingTap
                    )
                    Categories(
                        isLoading = false,
                        categories = (state as HomeState.Data).categories
                    )

                    if ((state as HomeState.Data).registered.isNotEmpty()) {
                        TrendingCompetitions(
                            text = "Your registrations",
                            competitions = (state as HomeState.Data).registered,
                            onCompetitionTap = onRegisteredTap
                        )
                    }
                }

                is HomeState.Error -> {
                    CenteredView {
                        ServerConnectionError(
                            errorText = (state as HomeState.Error).message,
                            onRetryClick = { viewModel.getTrendingCompetitions() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingCompetitions(
    text: String = "What's trending",
    competitions: List<Competition>? = null,
    onCompetitionTap: (String) -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 8.dp)
    )

    if (competitions.isNullOrEmpty()) {
        CenteredView {
            Text(text = "Nothing here yet", style = MaterialTheme.typography.subtitle1)
        }
    } else {
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
            items(competitions) { competition ->
                TrendingCompetitionCard(competition, onTap = onCompetitionTap)
            }
        }
    }
}

@Composable
fun TrendingCompetitionCard(
    competition: Competition,
    onTap: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onTap(competition.id) }
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Text(text = competition.title, style = MaterialTheme.typography.subtitle1)
            Text(
                text = "${competition.startDate.parseAndFormatAsDate()} to ${competition.endDate.parseAndFormatAsDate()}",
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = competition.category.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.US
                    ) else it.toString()
                },
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun Categories(
    isLoading: Boolean = true,
    categories: List<String>? = null,
) {
    Text(
        text = "Categories",
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 8.dp)
    )

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(start = 24.dp, top = 4.dp)
        )
    } else {
        LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
            items(categories ?: emptyList()) { category ->
                CategoryCard(category)
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: String,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(all = 16.dp)
        ) {
            Text(text = category, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    BetterFitTheme {
        HomeScreen(
            onTrendingTap = { },
            onRegisteredTap = { }
        )
    }
}
