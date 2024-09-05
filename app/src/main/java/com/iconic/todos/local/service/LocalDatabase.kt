package com.iconic.todos.local.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iconic.todos.local.dao.TodoDao
import com.iconic.todos.local.model.TodoLocal

@Database(entities = [TodoLocal::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}