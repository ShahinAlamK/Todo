package com.iconic.todos.domain.models

import com.google.firebase.firestore.DocumentId

data class UserModel(
    @DocumentId
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val profile: String = "",
    val date: Long = System.currentTimeMillis()
)
