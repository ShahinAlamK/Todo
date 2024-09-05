package com.iconic.todos.domain.repository

import com.iconic.todos.domain.models.Todo
import com.iconic.todos.utils.AppResponse
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getAllTodos(id: String): Flow<AppResponse<List<Todo>>>

    suspend fun addTodo(id: String, todo: Todo): Flow<AppResponse<String>>

    suspend fun deleteTodo(id: String,todo: Todo): Flow<AppResponse<String>>

    suspend fun updateTodo(todo: Todo,currentId:String): Flow<AppResponse<String>>

    suspend fun deleteAllTodos(id: String): Flow<AppResponse<String>>

    suspend fun getTodoById(id: String,currentId:String): Flow<AppResponse<Todo>>


}