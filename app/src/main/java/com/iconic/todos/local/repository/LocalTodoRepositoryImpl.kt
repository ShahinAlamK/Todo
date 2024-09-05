package com.iconic.todos.local.repository

import com.iconic.todos.local.dao.TodoDao
import com.iconic.todos.local.model.TodoLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTodoRepositoryImpl @Inject constructor(private val todoDao: TodoDao)  {

    fun getAllTodos(): Flow<List<TodoLocal>> = todoDao.getAllTodos()

    suspend fun insertTodo(todoLocal: List<TodoLocal>) = todoDao.insertTodo(todoLocal)

}