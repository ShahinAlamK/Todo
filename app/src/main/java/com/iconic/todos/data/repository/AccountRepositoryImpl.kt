package com.iconic.todos.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.iconic.todos.domain.models.UserModel
import com.iconic.todos.domain.repository.AccountRepository
import com.iconic.todos.utils.AppResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    AccountRepository {

    override suspend fun getProfile(uid: String): Flow<AppResponse<UserModel>> = flow {
        emit(AppResponse.Loading)
        try {
            firestore.collection("users").document(uid).snapshots().collect {
                val userModel = it.toObject(UserModel::class.java)
                emit(AppResponse.Success(userModel!!))
            }
        } catch (e: Exception) {
            emit(AppResponse.Failure(e))
        }
    }

    override suspend fun updateProfile(
        uid: String,
        username: String,
        profile: String
    ): Flow<AppResponse<String>> = flow {
        emit(AppResponse.Loading)
        firestore.collection("users").document(uid).update(
            mapOf(
                "username" to username,
                "profile" to profile
            )
        ).await()
        emit(AppResponse.Success("Successfully updated"))
    }

    override suspend fun deleteProfile(): Flow<Result<Unit>> {
        TODO("Not yet implemented")
    }


    override suspend fun createProfile(userModel: UserModel) {
        firestore.collection("users").document(userModel.uid).set(userModel).await()
    }
}