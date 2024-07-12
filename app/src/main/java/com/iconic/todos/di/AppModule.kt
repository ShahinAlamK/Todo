package com.iconic.todos.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.iconic.todos.data.repository.AccountRepositoryImpl
import com.iconic.todos.data.repository.AuthRepositoryImpl
import com.iconic.todos.data.repository.TodoRepositoryImpl
import com.iconic.todos.domain.repository.AccountRepository
import com.iconic.todos.domain.repository.AuthRepository
import com.iconic.todos.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAuthServices(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun providesAccountRepository(firestore: FirebaseFirestore): AccountRepository = AccountRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun providesTodoRepository(firestore: FirebaseFirestore): TodoRepository = TodoRepositoryImpl(firestore)



}