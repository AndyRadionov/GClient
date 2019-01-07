package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.utils.AuthStates
import com.radionov.githubclient.utils.RxComposers

/**
 * @author Andrey Radionov
 */
class MainViewModel(authRepository: GithubAuthRepository,
                    private val githubRepository: GithubRepository,
                    private val rxComposers: RxComposers
) : ViewModel() {


    private val authLiveData = MutableLiveData<AuthStates>()

    init {
        val authState = if (authRepository.getLocalToken().isEmpty()) AuthStates.OUT else AuthStates.LOGGED
        authLiveData.postValue(authState)
    }

    fun subscribeAuthState() = authLiveData
}
