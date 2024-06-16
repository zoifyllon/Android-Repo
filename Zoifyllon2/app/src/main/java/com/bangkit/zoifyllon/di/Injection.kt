package com.bangkit.zoifyllon.di

import android.content.Context
import com.bangkit.zoifyllon.api.ApiConfig
import com.bangkit.zoifyllon.data.pref.UserPreference
import com.bangkit.zoifyllon.data.pref.dataStore
import com.bangkit.zoifyllon.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository = runBlocking {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = pref.getSession().first()
        val apiService = ApiConfig.getApiService(user.token)
        UserRepository.getInstance(apiService, pref)
    }
}
