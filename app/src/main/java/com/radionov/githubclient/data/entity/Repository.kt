package com.radionov.githubclient.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Andrey Radionov
 */
@Parcelize
@Entity(tableName = "repositories")
data class Repository(
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("stargazers_count") val starsCount: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("license") val license: License?
) : Parcelable

@Parcelize
data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
) : Parcelable

@Parcelize
data class License(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String
) : Parcelable
