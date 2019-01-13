package com.radionov.githubclient.data.repository

import com.radionov.githubclient.data.datasource.local.db.CommitsDao
import com.radionov.githubclient.data.datasource.local.db.ReposDao
import com.radionov.githubclient.data.entity.CommitResponse
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Completable

/**
 * @author Andrey Radionov
 */
class LocalRepository(private val reposDao: ReposDao,
                      private val commitsDao: CommitsDao) {

    fun getRepos() = reposDao.getRepos()

    fun saveRepos(repos: List<Repository>) =
        Completable.fromAction {
            reposDao.saveRepos(repos)
        }


    fun getCommit(repo: String) = commitsDao.getCommit(repo)

    fun addCommit(commit: CommitResponse) =
        Completable.fromAction {
            commitsDao.addCommit(commit)
        }

    fun clear() =
        Completable.fromAction {
            reposDao.clear()
            commitsDao.clear()
        }
}
