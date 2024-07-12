package com.iconic.todos.ui.screens.home

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
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        getAllTodos()
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            todoRepository.getAllTodos(authRepository.currentUser!!.uid).collect {
                when (it) {
                    is AppResponse.Failure -> {
                        homeUiState = homeUiState.copy(isLoading = false)
                        homeUiState = homeUiState.copy(error = it.msg.localizedMessage)
                    }

                    is AppResponse.Loading -> {
                        homeUiState = homeUiState.copy(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        homeUiState = homeUiState.copy(todos = it.data)
                        homeUiState = homeUiState.copy(isLoading = false)
                    }
                }
            }
        }
    }


    fun deleteToggles() {
        homeUiState = homeUiState.copy(delete = !homeUiState.delete)
    }

    private var todo by mutableStateOf(Todo())
    fun setDelete(value: Todo) {
        todo = value
    }

    fun deleteTodo() {
        viewModelScope.launch {
            todoRepository.deleteTodo(authRepository.currentUser!!.uid, todo).collect {
                when (it) {
                    is AppResponse.Failure -> {
                        homeUiState = homeUiState.copy(isDelete = false)
                        homeUiState = homeUiState.copy(error = it.msg.localizedMessage)
                    }

                    AppResponse.Loading -> {
                        homeUiState = homeUiState.copy(isDelete = true)
                    }

                    is AppResponse.Success -> {
                        homeUiState = homeUiState.copy(isDelete = false)
                    }
                }
            }
        }
    }

    fun deleteAllTodo() {
        viewModelScope.launch {
            todoRepository.deleteAllTodos(authRepository.currentUser!!.uid).collect {
                when (it) {
                    is AppResponse.Failure -> {}
                    AppResponse.Loading -> {}
                    is AppResponse.Success -> {}
                }
            }
        }

    }
}

