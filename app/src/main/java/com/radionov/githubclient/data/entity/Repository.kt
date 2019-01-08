package com.radionov.githubclient.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Repository(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("stargazers_count") val starsCount: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("owner") val owner: Owner?,
    @SerializedName("license") val license: License?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        null,
        null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeString(description)
        parcel.writeInt(forksCount)
        parcel.writeInt(starsCount)
        parcel.writeInt(watchersCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }
    }
}

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String)

data class License(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String)
