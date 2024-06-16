package com.bangkit.zoifyllon.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bangkit.zoifyllon.api.ApiService
import com.bangkit.zoifyllon.api.LoginRequest
import com.bangkit.zoifyllon.api.RegisterRequest
import com.bangkit.zoifyllon.data.datamodel.RegisterResponse
import com.bangkit.zoifyllon.data.pref.UserModel
import com.bangkit.zoifyllon.data.pref.UserPreference
import com.bangkit.zoifyllon.data.datamodel.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = RegisterRequest(username, email, password)
            val response = apiService.register(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (e.code() == 400 && errorBody != null && errorBody.contains("Email is already taken")) {
                emit(Result.Error("Email is already taken"))
            } else {
                emit(Result.Error("HTTP error: ${e.message}"))
            }
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }

    suspend fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getUserById(id: String): LiveData<Result<UserModel>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUserById(id)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }

    suspend fun getAllUsers(): LiveData<Result<List<UserModel>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllUsers()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }

    suspend fun updateUserById(id: String, user: UserModel): LiveData<Result<UserModel>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.updateUserById(id, user)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error("HTTP error: ${e.message}"))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}
