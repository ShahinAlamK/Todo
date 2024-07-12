package com.iconic.todos.utils

sealed class AppResponse<out T>{
    class Success<out T>(val data: T) : AppResponse<T>()
    class Failure<out T>(val msg: Exception) : AppResponse<T>()
    data object Loading : AppResponse<Nothing>()
}
