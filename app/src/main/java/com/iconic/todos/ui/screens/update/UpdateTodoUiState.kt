package com.iconic.todos.ui.screens.update

import com.iconic.todos.domain.models.Todo

data class UpdateTodoUiState(
    val todo: Todo = Todo(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEnable: Boolean = true,
)