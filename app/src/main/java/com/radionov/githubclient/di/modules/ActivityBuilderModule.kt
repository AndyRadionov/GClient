package com.radionov.githubclient.di.modules

import com.radionov.githubclient.ui.main.MainActivity
import com.radionov.githubclient.ui.auth.OAuthActivity
import com.radionov.githubclient.ui.details.DetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Andrey Radionov
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindOauthActivity(): OAuthActivity

    @ContributesAndroidInjector
    abstract fun bindDetailsActivity(): DetailsActivity
}
