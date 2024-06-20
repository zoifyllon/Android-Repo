package com.bangkit.zoifyllon.api

import com.bangkit.zoifyllon.data.datamodel.DetectResponse
import com.bangkit.zoifyllon.data.datamodel.LoginResponse
import com.bangkit.zoifyllon.data.datamodel.RegisterResponse
import com.bangkit.zoifyllon.data.pref.UserModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

data class RegisterRequest(val name: String, val email: String, val password: String, val profileImage: String? = null)
data class LoginRequest(val email: String, val password: String)

interface ApiService {

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: String): UserModel

    @GET("/api/users")
    suspend fun getAllUsers(): List<UserModel>

    @PATCH("/api/users/{id}")
    suspend fun updateUserById(
        @Path("id") id: String,
        @Body user: UserModel
    ): UserModel

    @Multipart
    @POST("/detect")
    fun detectDisease(
        @Part detectImage: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<DetectResponse>
    }

