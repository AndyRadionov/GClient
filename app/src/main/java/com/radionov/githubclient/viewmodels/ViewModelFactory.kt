package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.radionov.githubclient.interactor.ReposInteractor
import com.radionov.githubclient.utils.RxComposers
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
class ViewModelFactory @Inject constructor(
    private val reposInteractor: ReposInteractor,
    private val rxComposers: RxComposers
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(reposInteractor, rxComposers) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(reposInteractor, rxComposers) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(reposInteractor, rxComposers) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel type was not found")
            }
        }
    }
}
