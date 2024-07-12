package com.iconic.todos.ui.screens.home

import com.iconic.todos.domain.models.Todo

data class HomeUiState(
    val isLoading: Boolean = false,
    val isDelete: Boolean = false,
    val error: String? = null,
    val delete: Boolean = false,
    val todos: List<Todo> = emptyList(),
)
