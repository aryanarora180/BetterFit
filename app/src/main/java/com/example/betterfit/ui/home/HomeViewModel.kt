package com.example.betterfit.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.HomeState
import com.example.betterfit.data.OperationResult
import com.example.betterfit.helper.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreUtils: DataStoreUtils
) : ViewModel() {

    val state = mutableStateOf<HomeState>(HomeState.Loading)

    fun getTrendingCompetitions() {
        state.value = HomeState.Loading
        viewModelScope.launch {
            when (val result = repository.getHomeContent(dataStoreUtils.getAuthToken() ?: "")) {
                is OperationResult.Success -> {
                    state.value = HomeState.Data(
                        trending = result.data.first,
                        registered = result.data.second
                    )
                }
                is OperationResult.Error -> {
                    state.value = HomeState.Error(result.message ?: "")
                }
            }
        }
    }
}