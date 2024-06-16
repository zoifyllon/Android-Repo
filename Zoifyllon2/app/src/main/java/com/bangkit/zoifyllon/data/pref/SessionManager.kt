package com.bangkit.zoifyllon.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.zoifyllon.R

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        private const val USER_TOKEN = "user_token"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        with(prefs.edit()) {
            putString(USER_TOKEN, token)
            apply()
        }
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Function to clear auth token (logout)
     */
    fun clearAuthToken() {
        prefs.edit().remove(USER_TOKEN).apply()
    }

    /**
     * Check if a user is logged in (token exists)
     */
    fun isLoggedIn(): Boolean {
        return fetchAuthToken() != null
    }
}
