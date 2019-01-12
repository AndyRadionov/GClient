package com.radionov.githubclient.data.datasource.local.db

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.radionov.githubclient.data.entity.CommitResponse
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
interface CommitsDao {

    @Query("SELECT * FROM commits WHERE repo=:repo")
    fun getCommit(repo: String) : Single<CommitResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCommits(commit: CommitResponse)

    @Query("DELETE FROM commits")
    fun clear()
}
