package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.utils.AuthStates
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * @author Andrey Radionov
 */
class MainViewModel(authRepository: GithubAuthRepository,
                    private val githubRepository: GithubRepository,
                    private val rxComposers: RxComposers
) : ViewModel() {

    private var disposable: Disposable? = null
    private val authLiveData = MutableLiveData<AuthStates>()
    private val reposLiveData = MutableLiveData<Pair<Responses, List<Repository>>>()

    init {
        val authState = if (authRepository.getLocalToken().isEmpty())
            AuthStates.OUT else AuthStates.LOGGED
        authLiveData.postValue(authState)
    }

    fun subscribeAuthState() = authLiveData

    fun getRepositories() {
        dispose()
        disposable = githubRepository.getRepositories()
            .compose(rxComposers.getObservableComposer())
            .subscribe({ repos ->
                if (repos.isNullOrEmpty()) {
                    reposLiveData.postValue(Pair(Responses.FAIL, Collections.emptyList()))
                } else {
                    reposLiveData.postValue(Pair(Responses.SUCCESS, repos))
                }
            }, { reposLiveData.postValue(Pair(Responses.FAIL, Collections.emptyList())) })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose() {
        disposable?.dispose()
    }
}
