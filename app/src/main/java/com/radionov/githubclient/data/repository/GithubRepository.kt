package com.radionov.githubclient.data.repository

import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.network.GithubApi
import com.radionov.githubclient.data.entity.CommitResponse
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Observable

/**
 * @author Andrey Radionov
 */
class GithubRepository(private val api: GithubApi,
                       private val prefs: Prefs) {

    fun getRepositories(): Observable<List<Repository>> {
        return api.getRepositories(prefs.getToken())
    }

    fun getLastCommit(owner: String, repo: String): Observable<CommitResponse> {
        return api.getCommits(owner, repo, prefs.getToken())
            .map { commits -> commits.first() }
    }
}
