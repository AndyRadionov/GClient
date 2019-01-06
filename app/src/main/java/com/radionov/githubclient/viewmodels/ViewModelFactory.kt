package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.radionov.githubclient.data.repository.AuthRepository
import com.radionov.githubclient.utils.RxComposers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
class ViewModelFactory @Inject constructor(
    private val authRepository: AuthRepository,
    private val rxComposers: RxComposers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel() as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(authRepository, rxComposers) as T
        } else {
            throw IllegalArgumentException("ViewModel type was not found")
        }
    }
}
