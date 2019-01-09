package com.radionov.githubclient.data.datasource.network

import com.radionov.githubclient.data.entity.Commit
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Andrey Radionov
 */
interface GithubApi {

    @GET("user/repos")
    fun getRepositories(@Query("access_token") token: String): Observable<List<Repository>>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(
        @Query("access_token") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Observable<List<Commit>>
}
