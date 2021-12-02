package com.example.betterfit.ui.competitions.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    val state = mutableStateOf<HomeState>(HomeState.Loading)

    private fun registerForCompetition() {

    }
}