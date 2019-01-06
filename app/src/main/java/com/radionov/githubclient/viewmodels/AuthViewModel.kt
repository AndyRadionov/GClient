package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.ViewModel
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.repository.AuthRepository

/**
 * @author Andrey Radionov
 */
class AuthViewModel(private val authRepository: AuthRepository,
                    private val prefs: Prefs
) : ViewModel() {
}