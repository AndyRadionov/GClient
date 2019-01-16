package com.radionov.githubclient.interactor

import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.data.repository.LocalRepository

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

    fun getRepositories() {
        githubRepository.getRepositories()
    }

    fun getLastCommit(owner: String, repo: String) {

    }
}