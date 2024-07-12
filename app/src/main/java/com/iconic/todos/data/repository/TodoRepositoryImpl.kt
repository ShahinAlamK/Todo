package com.iconic.todos.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.iconic.todos.domain.models.Todo
import com.iconic.todos.domain.repository.TodoRepository
import com.iconic.todos.utils.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TodoRepository {
    private val collectionReference = firestore.collection("todos")

    override fun getAllTodos(id: String): Flow<AppResponse<List<Todo>>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(id).collection("todo")
                .orderBy("date", Query.Direction.DESCENDING).snapshots().collect {
                val todos = it.toObjects(Todo::class.java)
                emit(AppResponse.Success(todos))
            }
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun addTodo(id: String, todo: Todo): Flow<AppResponse<String>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(id).collection("todo").document().set(todo).await()
            emit(AppResponse.Success("Successfully added"))
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun deleteTodo(id: String, todo: Todo): Flow<AppResponse<String>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(id).collection("todo").document(todo.id).delete()
                .await()
            emit(AppResponse.Success("Successfully deleted"))
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun updateTodo(todo: Todo): Flow<AppResponse<String>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(todo.id).collection("todo").document(todo.id).set(todo)
                .await()
            emit(AppResponse.Success("Successfully deleted"))
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun deleteAllTodos(id: String): Flow<AppResponse<String>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(id).collection("todo").get().await().documents.forEach {
                it.reference.delete()
            }
            emit(AppResponse.Success("Successfully deleted"))
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun getTodoById(id: String): Flow<AppResponse<Todo>> = flow {
        emit(AppResponse.Loading)
        try {
            collectionReference.document(id).collection("todo").document(id).snapshots().collect {
                val todo = it.toObject(Todo::class.java)
                emit(AppResponse.Success(todo!!))
            }
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }
}