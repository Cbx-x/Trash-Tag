package com.mindmatrix.app1.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindmatrix.app1.model.User
import com.mindmatrix.app1.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _userState = mutableStateOf<Result<User>?>(null)
    val userState: State<Result<User>?> = _userState

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    fun register(user: User, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _userState.value = repository.registerUser(user, password)
            _loading.value = false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _userState.value = repository.loginUser(email, password)
            _loading.value = false
        }
    }

    fun fetchCurrentUserProfile() {
        val currentUid = repository.getCurrentUser()?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                _loading.value = true
                _userState.value = repository.getUserProfile(currentUid)
                _loading.value = false
            }
        }
    }

    fun fetchLeaderboard() {
        viewModelScope.launch {
            repository.getAllUsers()
                .catch { e -> 
                    // Fail gracefully if index is not ready
                    _allUsers.value = emptyList()
                }
                .collect {
                    _allUsers.value = it
                }
        }
    }

    fun updateProfile(name: String, area: String, phone: String) {
        val currentUid = repository.getCurrentUser()?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                _loading.value = true
                val updates = mapOf(
                    "fullName" to name,
                    "area" to area,
                    "phoneNumber" to phone
                )
                val result = repository.updateUserProfile(currentUid, updates)
                if (result.isSuccess) {
                    fetchCurrentUserProfile()
                }
                _loading.value = false
            }
        }
    }

    fun incrementRewards() {
        val currentUid = repository.getCurrentUser()?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                repository.incrementUserRewards(currentUid, 5)
                fetchCurrentUserProfile()
            }
        }
    }

    fun logout() {
        repository.logout()
        _userState.value = null
    }

    fun isUserLoggedIn() = repository.getCurrentUser() != null
}
