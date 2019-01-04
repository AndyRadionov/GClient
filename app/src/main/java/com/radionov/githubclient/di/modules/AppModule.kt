package com.radionov.githubclient.di.modules

import androidx.annotation.NonNull
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

//    @NonNull
//    @Provides
//    @Singleton
//    fun provideRepository(api: Api) =

    @NonNull
    @Provides
    @Singleton
    fun provideRxComposers() = RxComposers(Schedulers.io(), AndroidSchedulers.mainThread())
}
