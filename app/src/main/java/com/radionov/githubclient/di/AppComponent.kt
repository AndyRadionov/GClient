package com.radionov.githubclient.di

import android.app.Application
import com.radionov.githubclient.App
import com.radionov.githubclient.di.modules.ActivityBuilderModule
import com.radionov.githubclient.di.modules.AppModule
import com.radionov.githubclient.di.modules.NetworkModule
import com.radionov.githubclient.di.modules.PersistenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Andrey Radionov
 */
@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PersistenceModule::class,
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }
}