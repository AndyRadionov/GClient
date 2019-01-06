package com.radionov.githubclient.data.datasource.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Andrey Radionov
 */
interface GithubAuthApi {

    @GET(".")
    fun getToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): Single<String>
}
