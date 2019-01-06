package com.radionov.githubclient.data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class CommitResponse(
    @SerializedName("sha") val sha: String,
    @SerializedName("commit") val commit: Commit)

data class Commit(
    @SerializedName("message") val message: String,
    @SerializedName("author") val author: Author)

data class Author(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String)
