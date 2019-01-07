package com.radionov.githubclient.di.modules

import android.app.Application
import android.preference.PreferenceManager
import android.support.annotation.NonNull
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.network.GithubAuthApi
import com.radionov.githubclient.data.repository.GithubAuthRepository
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
    fun provideAuthRepository(authApi: GithubAuthApi, prefs: Prefs) = GithubAuthRepository(authApi, prefs)

    @NonNull
    @Provides
    @Singleton
    fun providePrefs(app: Application): Prefs =
        Prefs(PreferenceManager.getDefaultSharedPreferences(app))

    @NonNull
    @Provides
    @Singleton
    fun provideRxComposers() = RxComposers(Schedulers.io(), AndroidSchedulers.mainThread())
}
