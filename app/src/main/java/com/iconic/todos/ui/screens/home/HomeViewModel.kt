package com.iconic.todos.ui.screens.home

import android.util.Log
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
                homeUiState = when (it) {
                    is AppResponse.Failure -> {
                        HomeUiState(error = it.msg.localizedMessage)
                    }

                    is AppResponse.Loading -> {
                        HomeUiState(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        HomeUiState(todos = it.data)
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
                homeUiState = when (it) {
                    is AppResponse.Failure -> {
                        homeUiState.copy(error = it.msg.localizedMessage)
                    }

                    AppResponse.Loading -> {
                        homeUiState.copy(isDelete = true)
                    }

                    is AppResponse.Success -> {
                        homeUiState.copy(isDelete = false)
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

