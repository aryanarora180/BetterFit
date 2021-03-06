package com.example.betterfit.ui.competitions.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.CompetitionDetailsState
import com.example.betterfit.data.OperationResult
import com.example.betterfit.helper.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreUtils: DataStoreUtils
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

        state.value = CompetitionDetailsState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                repository.registerToCompetition(_competitionId, dataStoreUtils.getAuthToken())) {
                is OperationResult.Success -> {
                    state.value = CompetitionDetailsState.TakePayment(result.data.clientSecret)
                }

                is OperationResult.Error -> {
                    state.value = stateBackup
                }
            }
        }
    }
}