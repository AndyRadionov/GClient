package com.radionov.githubclient.data.datasource.network

import com.radionov.githubclient.data.entity.Commit
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Andrey Radionov
 */
interface GithubApi {

    @GET("user/repos")
    fun getRepositories(): Observable<Repository>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Observable<Commit>
}
