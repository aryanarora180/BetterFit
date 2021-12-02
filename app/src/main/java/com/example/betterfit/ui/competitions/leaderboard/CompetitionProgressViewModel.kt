package com.example.betterfit.ui.competitions.leaderboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.OperationResult
import com.example.betterfit.data.ProgressState
import com.example.betterfit.helper.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionProgressViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    private lateinit var _competitionId: String

    val state = mutableStateOf<ProgressState>(ProgressState.Loading)

    fun getProgress(competitionId: String) {
        _competitionId = competitionId
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getLeaderboard(competitionId)) {
                is OperationResult.Success -> {
                    state.value = ProgressState.Data(result.data.first, result.data.second)
                }
                is OperationResult.Error -> {
                    state.value = ProgressState.Error(result.getErrorMessage())
                }
            }
        }
    }
}