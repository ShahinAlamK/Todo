package com.iconic.todos.ui.screens.create

import com.iconic.todos.domain.models.Todo

data class CreateTodoUiState(
    val todo: Todo = Todo(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEnable: Boolean = false,
    val openDialog: Boolean = false,
)