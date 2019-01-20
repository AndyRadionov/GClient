package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.interactor.ReposInteractor
import com.radionov.githubclient.utils.AuthStates
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * @author Andrey Radionov
 */
class MainViewModel(private val reposInteractor: ReposInteractor,
                    private val rxComposers: RxComposers
) : ViewModel() {

    private var disposable: Disposable? = null
    private val authLiveData = MutableLiveData<AuthStates>()
    private val reposLiveData = MutableLiveData<Pair<Responses, List<Repository>>>()

    init {
        val authState = if (reposInteractor.isAuthorized()) AuthStates.LOGGED else AuthStates.OUT
        authLiveData.postValue(authState)
    }

    fun subscribeAuthState() = authLiveData
    fun subscribeRepos() = reposLiveData

    fun signOut() {
        reposInteractor.signOut()
        authLiveData.postValue(AuthStates.OUT)
    }

    fun getRepositories() {
        dispose()
        disposable = reposInteractor.getRepositories()
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
