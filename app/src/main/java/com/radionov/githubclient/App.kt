package com.radionov.githubclient

import com.radionov.githubclient.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/**
 * @author Andrey Radionov
 */
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .builder()
            .app(this)
            .build()
}
