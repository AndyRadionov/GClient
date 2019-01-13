package com.radionov.githubclient.data.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
@Entity(
    tableName = "commits",
    foreignKeys = [ForeignKey(
        entity = Repository::class,
        parentColumns = ["name"],
        childColumns = ["repo"],
        onDelete = CASCADE
    )],
    indices = [Index(value = ["repo"])]
)
data class CommitResponse(
    @SerializedName("sha") @PrimaryKey val sha: String,
    @SerializedName("commit") @Embedded val commit: Commit,
    var repo: String? = null
)

data class Commit(
    @SerializedName("message") val message: String,
    @SerializedName("author") @Embedded val author: Author
)

data class Author(
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String
) {
    override fun toString(): String {
        return "$name, ${date.substringBefore("T")}"
    }
}
