package com.radionov.githubclient.data.datasource.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.radionov.githubclient.data.entity.Repository

/**
 * @author Andrey Radionov
 */
@Database(entities = [Repository::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val reposDao: ReposDao
    abstract val commitsDao: CommitsDao
}