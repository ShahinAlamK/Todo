package com.iconic.todos.domain.models

import com.google.firebase.firestore.DocumentId

data class Todo(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val color: Int = 0,
    val date: Long = System.currentTimeMillis()
)
