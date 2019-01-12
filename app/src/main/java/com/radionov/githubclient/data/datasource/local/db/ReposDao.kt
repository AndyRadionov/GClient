package com.radionov.githubclient.data.datasource.local.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.radionov.githubclient.data.entity.Repository
import io.reactivex.Flowable

/**
 * @author Andrey Radionov
 */
@Dao
interface ReposDao {

    @Query("SELECT * FROM repositories")
    fun getRepos() : Flowable<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepos(repos: List<Repository>)

    @Query("DELETE FROM repositories")
    fun clear()
}
