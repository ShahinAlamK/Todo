package com.iconic.todos.ui.screens.register

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isRegister: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val isEnable: Boolean = false
)
