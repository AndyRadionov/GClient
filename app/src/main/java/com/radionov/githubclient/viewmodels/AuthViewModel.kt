package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.disposables.Disposable

/**
 * @author Andrey Radionov
 */
class AuthViewModel(private val authRepository: GithubAuthRepository,
                    private val rxComposers: RxComposers) : ViewModel() {

    private var disposable: Disposable? = null
    private val responseLiveData = MutableLiveData<Responses>()

    fun subscribeResponse() = responseLiveData

    fun getToken(accessCode: String) {
        dispose()
        disposable = authRepository.getRemoteToken(accessCode)
            .compose(rxComposers.getSingleComposer())
            .subscribe({ token ->
                if (TextUtils.isEmpty(token)) {
                    responseLiveData.postValue(Responses.FAIL)
                } else {
                    responseLiveData.postValue(Responses.SUCCESS)
                }
            }, { e -> responseLiveData.postValue(Responses.FAIL) })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose() {
        disposable?.dispose()
    }
}