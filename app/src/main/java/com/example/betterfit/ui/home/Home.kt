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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betterfit.data.Competition
import com.example.betterfit.data.HomeState
import com.example.betterfit.ui.CenteredView
import com.example.betterfit.ui.ServerConnectionError
import com.example.betterfit.ui.theme.BetterFitTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), onCompetitionTap: (String) -> Unit) {
    val state by viewModel.state

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (state) {
                is HomeState.Loading -> {
                    TrendingCompetitions(isLoading = true, onCompetitionTap = { })
                    Categories(isLoading = true)
                }

                is HomeState.Data -> {
                    TrendingCompetitions(
                        isLoading = false,
                        competitions = (state as HomeState.Data).trending,
                        onCompetitionTap = onCompetitionTap
                    )
                    Categories(
                        isLoading = false,
                        categories = (state as HomeState.Data).categories
                    )
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
    isLoading: Boolean = true,
    competitions: List<Competition>? = null,
    onCompetitionTap: (String) -> Unit
) {
    Text(
        text = "What's trending",
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 8.dp)
    )

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.padding(start = 24.dp, top = 4.dp)
        )
    } else {
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
            items(competitions ?: emptyList()) { competition ->
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
                text = "${competition.startDate} to ${competition.endDate}",
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = competition.category,
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
        style = MaterialTheme.typography.h5,
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
        HomeScreen { }
    }
}
