package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.radionov.githubclient.data.entity.Commit
import com.radionov.githubclient.data.entity.CommitResponse
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.interactor.ReposInteractor
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.disposables.Disposable

/**
 * @author Andrey Radionov
 */
class DetailsViewModel(private val reposInteractor: ReposInteractor,
                       private val rxComposers: RxComposers
) : ViewModel() {

    private var disposable: Disposable? = null
    private val commitLiveData = MutableLiveData<Pair<Responses, CommitResponse?>>()

    fun subscribeCommit() = commitLiveData

    fun getLastCommit(owner: String, repo: String) {
        dispose()
        disposable = reposInteractor.getLastCommit(owner, repo)
            .compose(rxComposers.getObservableComposer())
            .subscribe({ commit ->
                val response = if (commit == null) Responses.FAIL else Responses.SUCCESS
                commitLiveData.postValue(Pair(response, commit))
            }, { commitLiveData.postValue(Pair(Responses.FAIL, null)) })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose() {
        disposable?.dispose()
    }
}
