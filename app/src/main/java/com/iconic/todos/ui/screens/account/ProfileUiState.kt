package com.iconic.todos.ui.screens.account

import com.iconic.todos.domain.models.UserModel

data class ProfileUiState(
    val userModel: UserModel = UserModel(),
    val isLoading: Boolean = false,
    val isUpdate: Boolean = false,
    val error: String? = null
)
