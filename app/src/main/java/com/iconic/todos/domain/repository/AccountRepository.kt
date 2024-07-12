package com.iconic.todos.domain.repository

import com.iconic.todos.domain.models.UserModel
import com.iconic.todos.utils.AppResponse
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun getProfile(uid: String): Flow<AppResponse<UserModel>>

    suspend fun updateProfile(uid: String,username: String, profile: String): Flow<AppResponse<String>>

    suspend fun deleteProfile(): Flow<Result<Unit>>

    suspend fun createProfile(userModel: UserModel)

}