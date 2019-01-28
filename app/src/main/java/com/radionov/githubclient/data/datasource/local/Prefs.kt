package com.radionov.githubclient.data.datasource.local

import android.content.SharedPreferences
import com.radionov.githubclient.utils.EMPTY_STRING

/**
 * @author Andrey Radionov
 */
class Prefs(private val prefs: SharedPreferences) {

    fun getToken(): String = prefs.getString(TOKEN_KEY, TOKEN_DEFAULT) as String

    fun setToken(token: String) {
        prefs.edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }

    fun removeToken() {
        prefs.edit()
            .remove(TOKEN_KEY)
            .apply()
    }

    companion object {
        private const val TOKEN_KEY = "token_key"
        private const val TOKEN_DEFAULT = EMPTY_STRING
    }
}
