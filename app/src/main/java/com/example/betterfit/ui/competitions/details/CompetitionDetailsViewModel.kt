package com.example.betterfit.ui.competitions.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.CompetitionDetailsState
import com.example.betterfit.data.HomeState
import com.example.betterfit.data.OperationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private lateinit var _competitionId: String

    val state = mutableStateOf<CompetitionDetailsState>(CompetitionDetailsState.Loading)

    fun getCompetitionDetails(competitionId: String) {
        _competitionId = competitionId
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getCompetitionDetails(competitionId)) {
                is OperationResult.Success -> {
                    state.value = CompetitionDetailsState.Data(result.data)
                }
                is OperationResult.Error -> {
                    state.value = CompetitionDetailsState.Error(result.getErrorMessage())
                }
            }
        }
    }

    fun registerForCompetition() {
        val stateBackup = state.value

        state.value = CompetitionDetailsState.Registering
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.registerToCompetition(_competitionId)) {
                is OperationResult.Success -> {

                }

                is OperationResult.Error -> {
                    state.value = stateBackup
                }
            }
        }
    }
}