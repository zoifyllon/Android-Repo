package com.bangkit.zoifyllon.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.zoifyllon.data.pref.UserModel
import com.bangkit.zoifyllon.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun login(email: String, password: String) = userRepository.login(email, password)

    suspend fun saveSession(user: UserModel) {
        userRepository.saveSession(user)
    }
}