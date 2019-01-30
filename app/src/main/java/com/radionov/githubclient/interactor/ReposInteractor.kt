package com.radionov.githubclient.interactor

import com.radionov.githubclient.data.entity.CommitResponse
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.data.repository.LocalRepository
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
class ReposInteractor(
    private val authRepository: GithubAuthRepository,
    private val githubRepository: GithubRepository,
    private val localRepository: LocalRepository
) {

    fun isAuthorized(): Boolean {
        return authRepository.getLocalToken().isNotEmpty()
    }

    fun signIn(accessCode: String): Single<String> {
        return authRepository.getRemoteToken(accessCode)
    }

    fun signOut() {
        authRepository.removeLocalToken()
    }

    fun getRepositories(): Observable<List<Repository>> {
        return githubRepository.getRepositories()
    }

    fun getLastCommit(owner: String, repo: String): Observable<CommitResponse> {
        return githubRepository.getLastCommit(owner, repo)
    }
}
