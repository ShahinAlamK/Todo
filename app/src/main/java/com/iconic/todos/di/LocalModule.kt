package com.iconic.todos.di

import android.content.Context
import androidx.room.Room
import com.iconic.todos.local.dao.TodoDao
import com.iconic.todos.local.service.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return  Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "todos.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(db: LocalDatabase): TodoDao = db.todoDao()

}