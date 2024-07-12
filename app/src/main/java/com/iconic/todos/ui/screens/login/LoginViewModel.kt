package com.iconic.todos.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iconic.todos.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var isLoggedIn: Boolean = authRepository.isLoggedIn

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    var autStatus by mutableStateOf<AutStatus>(AutStatus.Unauthenticated)


    fun updateState(value: LoginUiState) {
        loginUiState = LoginUiState(
            email = value.email,
            password = value.password,
            error = null,
            isPasswordVisible = value.isPasswordVisible,
            isEnable = formValid(value.email, value.password)
        )
    }

    fun passwordVisible() {
        loginUiState =
            loginUiState.copy(isPasswordVisible = !loginUiState.isPasswordVisible)
    }

    private fun formValid(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    fun login() {
        if (!formValid(loginUiState.email, loginUiState.password)) return
        loginUiState = loginUiState.copy(isLoading = true)

        viewModelScope.launch {
            authRepository.loginWithEmail(
                email = loginUiState.email,
                password = loginUiState.password
            ).onSuccess {
                autStatus = AutStatus.Authenticated
            }.onFailure {
                loginUiState = loginUiState.copy(error = it.message)
            }
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

}

sealed class AutStatus {
    data object Authenticated : AutStatus()
    data object Unauthenticated : AutStatus()

}