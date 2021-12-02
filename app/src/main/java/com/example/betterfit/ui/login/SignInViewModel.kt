package com.example.betterfit.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betterfit.data.AppRepository
import com.example.betterfit.data.HomeState
import com.example.betterfit.data.LoginState
import com.example.betterfit.data.OperationResult
import com.example.betterfit.helper.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AppRepository,
    private val dataStoreUtils: DataStoreUtils
) : ViewModel() {

    var state by mutableStateOf<LoginState>(LoginState.Waiting)

    fun signIn(idToken: String, authCode: String) {
        state = LoginState.SigningIn
        viewModelScope.launch(Dispatchers.IO) {
            state = when (val result = repository.signIn(idToken, authCode)) {
                is OperationResult.Success -> {
                    dataStoreUtils.storeAuthToken(result.data.token)
                    dataStoreUtils.storeUserId(result.data.userId)
                    LoginState.SignedIn
                }
                is OperationResult.Error -> {
                    Log.e("SignInViewModel", result.message ?: "Error")
                    LoginState.Error(result.message ?: "")
                }
            }
        }
    }
}