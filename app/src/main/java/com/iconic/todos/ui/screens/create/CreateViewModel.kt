package com.iconic.todos.ui.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iconic.todos.domain.models.Todo
import com.iconic.todos.domain.repository.AuthRepository
import com.iconic.todos.domain.repository.TodoRepository
import com.iconic.todos.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var createTodoUiState: CreateTodoUiState by mutableStateOf(CreateTodoUiState())
        private set

    fun updateCreateTodoUiState(todo: Todo) {
        createTodoUiState = createTodoUiState.copy(
            todo = todo,
            error = null,
            isEnable = validTodo()
        )
    }


    private fun validTodo(): Boolean {
        return createTodoUiState.todo.title.isNotEmpty() || createTodoUiState.todo.description.isNotEmpty()
    }

    fun createTodo() {
        viewModelScope.launch {
            todoRepository.addTodo(authRepository.currentUser!!.uid, createTodoUiState.todo)
                .collect {
                    when (it) {
                        is AppResponse.Failure -> {
                            createTodoUiState = createTodoUiState.copy(isLoading = false)
                            createTodoUiState =
                                createTodoUiState.copy(error = it.msg.localizedMessage)
                        }

                        AppResponse.Loading -> {
                            createTodoUiState = createTodoUiState.copy(isLoading = true)
                        }

                        is AppResponse.Success -> {
                           // createTodoUiState = createTodoUiState.copy(todo = Todo())
                            createTodoUiState = createTodoUiState.copy(isLoading = false)
                        }
                    }
                }
        }
    }
}