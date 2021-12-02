package com.example.betterfit.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.HomeState
import com.example.betterfit.data.OperationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    val state = mutableStateOf<HomeState>(HomeState.Loading)

    fun getTrendingCompetitions() {
        state.value = HomeState.Loading
        viewModelScope.launch {
            when (val result = repository.getTrendingCompetitions()) {
                is OperationResult.Success -> {
                    state.value = HomeState.Data(result.data)
                }
                is OperationResult.Error -> {
                    state.value = HomeState.Error(result.message ?: "")
                }
            }
        }
    }

    init {
        getTrendingCompetitions()
    }
}