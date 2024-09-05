package com.iconic.todos.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoLocal(
    @PrimaryKey
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val color: Int = 0,
    val date: Long = 0L
)
