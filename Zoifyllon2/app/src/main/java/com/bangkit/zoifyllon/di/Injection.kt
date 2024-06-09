package com.bangkit.zoifyllon.di

import android.content.Context
import com.bangkit.zoifyllon.data.pref.UserPreference
import com.bangkit.zoifyllon.data.pref.dataStore
import com.rempahpedia.rempahpedia.data.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}