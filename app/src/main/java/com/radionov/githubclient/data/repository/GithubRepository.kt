package com.radionov.githubclient.data.repository

import com.radionov.githubclient.data.entity.Commit
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Observable

/**
 * @author Andrey Radionov
 */
class GithubRepository(private val repository: GithubRepository) {

    fun getRepositories(): Observable<Repository> {
        return repository.getRepositories()
    }

    fun getCommits(owner: String, repo: String): Observable<Commit> {
        return repository.getCommits(owner, repo)
    }
}
