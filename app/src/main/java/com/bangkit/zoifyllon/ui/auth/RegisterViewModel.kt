package com.bangkit.zoifyllon.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.bangkit.zoifyllon.data.UserRepository
import com.bangkit.zoifyllon.data.Result
import com.bangkit.zoifyllon.data.datamodel.RegisterResponse

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun register(username: String, email: String, password: String): LiveData<Result<RegisterResponse>> {
        return userRepository.register(username, email, password)
    }
}