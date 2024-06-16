package com.bangkit.zoifyllon.data.pref

data class UserModel(
    val id: String,
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)