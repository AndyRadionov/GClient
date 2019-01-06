package com.radionov.githubclient.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.radionov.githubclient.data.repository.AuthRepository
import com.radionov.githubclient.utils.RESPONSES
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.disposables.Disposable

/**
 * @author Andrey Radionov
 */
class AuthViewModel(private val authRepository: AuthRepository,
                    private val rxComposers: RxComposers) : ViewModel() {

    private var disposable: Disposable? = null
    private val responseLiveData = MutableLiveData<RESPONSES>()

    fun subscribeResponse() = responseLiveData

    fun getToken(accessCode: String) {
        dispose()
        disposable = authRepository.getRemoteToken(accessCode)
            .compose(rxComposers.getSingleComposer())
            .subscribe({ token ->
                if (TextUtils.isEmpty(token)) {
                    responseLiveData.postValue(RESPONSES.FAIL)
                } else {
                    responseLiveData.postValue(RESPONSES.SUCCESS)
                }
            }, { e -> responseLiveData.postValue(RESPONSES.FAIL) })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    private fun dispose() {
        disposable?.dispose()
    }
}