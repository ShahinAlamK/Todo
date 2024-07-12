package com.iconic.todos.ui.screens.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLogin: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val isEnable: Boolean = false
)
