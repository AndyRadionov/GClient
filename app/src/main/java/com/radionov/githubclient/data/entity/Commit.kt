package com.radionov.githubclient.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
@Entity(tableName = "commits")
data class CommitResponse(
    @SerializedName("sha") @PrimaryKey val sha: String,
    @SerializedName("commit") val commit: Commit,
    var repo: String? = null
)

data class Commit(
    @SerializedName("message") val message: String,
    @SerializedName("author") val author: Author
)

data class Author(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String
) {
    override fun toString(): String {
        return "$name, ${date.substringBefore("T")}"
    }
}
