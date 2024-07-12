package com.iconic.todos.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iconic.todos.domain.models.UserModel
import com.iconic.todos.domain.repository.AccountRepository
import com.iconic.todos.domain.repository.AuthRepository
import com.iconic.todos.ui.screens.login.AutStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    var registerUiState by mutableStateOf(RegisterUiState())
        private set

    var authStatus by mutableStateOf<AutStatus>(AutStatus.Unauthenticated)

    fun updateState(value: RegisterUiState) {
        registerUiState = RegisterUiState(
            username = value.username,
            email = value.email,
            password = value.password,
            error = null,
            isEnable = formValid(value.username, value.email, value.password)
        )
    }

    fun passwordVisible() {
        registerUiState =
            registerUiState.copy(isPasswordVisible = !registerUiState.isPasswordVisible)
    }

    private fun formValid(username: String, email: String, password: String): Boolean {
        return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    fun register() {
        if (!formValid(
                registerUiState.username,
                registerUiState.email,
                registerUiState.password
            )
        ) return
        viewModelScope.launch {
            registerUiState = registerUiState.copy(isLoading = true)
            authRepository.createWithEmail(
                username = registerUiState.username,
                email = registerUiState.email,
                password = registerUiState.password
            ).onSuccess {
                val user = UserModel(
                    uid = authRepository.currentUser!!.uid,
                    username = registerUiState.username,
                    email = registerUiState.email
                )
                accountRepository.createProfile(user)
                authStatus = AutStatus.Authenticated
            }.onFailure {
                registerUiState = registerUiState.copy(error = it.message)
            }
        }
    }

}