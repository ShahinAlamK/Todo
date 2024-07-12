package com.iconic.todos.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    val currentUser: FirebaseUser?
    val isLoggedIn: Boolean

    suspend fun createWithEmail(
        username: String,
        email: String,
        password: String
    ): Result<FirebaseUser?>

    suspend fun loginWithEmail(email: String, password: String): Result<FirebaseUser?>
    suspend fun logOut(): Result<Unit>
}