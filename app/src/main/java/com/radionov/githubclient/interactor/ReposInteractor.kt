package com.radionov.githubclient.interactor

import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.data.repository.LocalRepository

/**
 * @author Andrey Radionov
 */
class ReposInteractor(
    private val githubRepository: GithubRepository,
    private val localRepository: LocalRepository
) {
}