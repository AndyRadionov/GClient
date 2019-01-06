package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.repository.AuthRepository
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
class ViewModelFactory @Inject constructor(
    private val authRepository: AuthRepository, private val prefs: Prefs
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel() as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(authRepository, prefs) as T
        } else {
            throw IllegalArgumentException("ViewModel type was not found")
        }
    }
}
