package com.bangkit.zoifyllon.data.datamodel

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message") val message: String,
    @field:SerializedName("data") val data: UserData?
)

data class UserData(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("image_url") val imageUrl: String,
    @field:SerializedName("token") val token: String
)
