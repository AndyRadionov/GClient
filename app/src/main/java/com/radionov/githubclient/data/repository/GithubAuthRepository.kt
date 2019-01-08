package com.radionov.githubclient.data.repository

import com.radionov.githubclient.BuildConfig.CLIENT_ID
import com.radionov.githubclient.BuildConfig.CLIENT_SECRET
import com.radionov.githubclient.data.datasource.local.CacheManager
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.network.GithubAuthApi
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
class GithubAuthRepository(
    private val authApi: GithubAuthApi,
    private val prefs: Prefs,
    cacheManager: CacheManager
) {

    init {
        cacheManager.clearWebViewCachesCustom()
    }

    fun getLocalToken() = prefs.getToken()

    fun removeLocalToken() {
        prefs.removeToken()
    }

    fun getRemoteToken(accessCode: String): Single<String> =
        authApi.getToken(CLIENT_ID, CLIENT_SECRET, accessCode)
            .map { token -> token.substringAfter("=").substringBefore("&") }
            .doOnSuccess { token -> prefs.setToken(token) }
}
