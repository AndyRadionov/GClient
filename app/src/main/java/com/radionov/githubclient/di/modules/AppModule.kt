package com.radionov.githubclient.di.modules

import android.app.Application
import android.preference.PreferenceManager
import android.support.annotation.NonNull
import com.radionov.githubclient.data.datasource.local.CacheManager
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.local.db.CommitsDao
import com.radionov.githubclient.data.datasource.local.db.ReposDao
import com.radionov.githubclient.data.datasource.network.GithubApi
import com.radionov.githubclient.data.datasource.network.GithubAuthApi
import com.radionov.githubclient.data.repository.GithubAuthRepository
import com.radionov.githubclient.data.repository.GithubRepository
import com.radionov.githubclient.data.repository.LocalRepository
import com.radionov.githubclient.interactor.ReposInteractor
import com.radionov.githubclient.utils.RxComposers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */

@Module
class AppModule {

    @NonNull
    @Provides
    @Singleton
    fun provideReposInteractor(
        authRepository: GithubAuthRepository,
        githubRepository: GithubRepository, localRepository: LocalRepository
    ) = ReposInteractor(authRepository, githubRepository, localRepository)

    @NonNull
    @Provides
    @Singleton
    fun provideLocalRepository(reposDao: ReposDao, commitsDao: CommitsDao) =
        LocalRepository(reposDao, commitsDao)

    @NonNull
    @Provides
    @Singleton
    fun provideAuthRepository(authApi: GithubAuthApi, prefs: Prefs, cacheManager: CacheManager) =
        GithubAuthRepository(authApi, prefs, cacheManager)

    @NonNull
    @Provides
    @Singleton
    fun provideGithubRepository(api: GithubApi, prefs: Prefs) = GithubRepository(api, prefs)

    @NonNull
    @Provides
    @Singleton
    fun provideRxComposers() = RxComposers(Schedulers.io(), AndroidSchedulers.mainThread())
}
