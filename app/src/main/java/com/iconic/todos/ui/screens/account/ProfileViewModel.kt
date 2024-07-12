package com.iconic.todos.ui.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iconic.todos.domain.models.UserModel
import com.iconic.todos.domain.repository.AccountRepository
import com.iconic.todos.domain.repository.AuthRepository
import com.iconic.todos.utils.AppResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var profileUiState: ProfileUiState by mutableStateOf(ProfileUiState())
        private set

    fun updateState(value: UserModel) {
        profileUiState = profileUiState.copy(userModel = value)
    }

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            accountRepository.getProfile(authRepository.currentUser!!.uid).collect {
                when (it) {
                    is AppResponse.Failure -> {
                        profileUiState = profileUiState.copy(error = it.msg.localizedMessage)
                        profileUiState = profileUiState.copy(isLoading = false)
                    }

                    AppResponse.Loading -> {
                        profileUiState = profileUiState.copy(isLoading = true)
                    }

                    is AppResponse.Success -> {
                        profileUiState = profileUiState.copy(userModel = it.data)
                        profileUiState = profileUiState.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            accountRepository.updateProfile(
                uid = authRepository.currentUser!!.uid,
                username = profileUiState.userModel.username,
                profile = profileUiState.userModel.profile
            ).collect {
                when (it) {
                    is AppResponse.Failure -> {
                        profileUiState = profileUiState.copy(isUpdate = false)
                        profileUiState = profileUiState.copy(error = it.msg.localizedMessage)
                    }

                    AppResponse.Loading -> {
                        profileUiState = profileUiState.copy(isUpdate = true)
                    }

                    is AppResponse.Success -> {
                        profileUiState = profileUiState.copy(isUpdate = false)
                    }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

}