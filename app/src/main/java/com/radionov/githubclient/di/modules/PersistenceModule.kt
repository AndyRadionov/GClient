package com.radionov.githubclient.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import android.preference.PreferenceManager
import android.support.annotation.NonNull
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.local.db.AppDatabase
import com.radionov.githubclient.data.datasource.local.db.CommitsDao
import com.radionov.githubclient.data.datasource.local.db.ReposDao
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
class PersistenceModule {

    @NonNull
    @Provides
    @Singleton
    fun providePrefs(app: Application): Prefs =
        Prefs(PreferenceManager.getDefaultSharedPreferences(app))

    @NonNull
    @Provides
    @Singleton
    fun provideReposDao(database: AppDatabase): ReposDao = database.reposDao

    @NonNull
    @Provides
    @Singleton
    fun provideCommitsDao(database: AppDatabase): CommitsDao = database.commitsDao

    @NonNull
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "github_client_db")
            .build()
}
