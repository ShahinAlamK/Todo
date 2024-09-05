package com.iconic.todos.ui.screens.update

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class UpdateViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val id = savedStateHandle.get<String>("id")

    var updateTodoUiState: UpdateTodoUiState by mutableStateOf(UpdateTodoUiState())
        private set

    fun updateCreateTodoUiState(todo: Todo) {
        updateTodoUiState = updateTodoUiState.copy(
            todo = todo,
            error = null,
            isEnable = validTodo()
        )
    }

    private fun validTodo(): Boolean {
        return updateTodoUiState.todo.title.isNotEmpty() || updateTodoUiState.todo.description.isNotEmpty()
    }

    init {
        viewModelScope.launch {
            todoRepository.getTodoById(id = id!!, currentId = authRepository.currentUser!!.uid)
                .collect {
                    updateTodoUiState = when (it) {
                        is AppResponse.Failure -> {
                            updateTodoUiState.copy(
                                isLoading = false,
                                error = it.msg.localizedMessage)
                        }

                        AppResponse.Loading -> {
                            updateTodoUiState.copy(isLoading = true)
                        }

                        is AppResponse.Success -> {
                            updateTodoUiState.copy(
                                isLoading = false,
                                todo = it.data)
                        }
                    }
                }
        }
    }

    fun updateTodo() {
        viewModelScope.launch {
            todoRepository.updateTodo(
                todo = updateTodoUiState.todo,
                currentId = authRepository.currentUser!!.uid
            ).collect {
                updateTodoUiState = when (it) {
                    is AppResponse.Failure -> {
                        updateTodoUiState.copy(
                            isLoading = false,
                            error = it.msg.localizedMessage)
                    }

                    AppResponse.Loading -> {
                        updateTodoUiState.copy(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        updateTodoUiState.copy(  isLoading = false,)
                    }
                }
            }
        }
    }
}