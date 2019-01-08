package com.radionov.githubclient.di.modules

import android.app.Application
import android.support.annotation.NonNull
import android.util.Log
import com.google.gson.Gson
import com.radionov.githubclient.BuildConfig.API_URL
import com.radionov.githubclient.BuildConfig.OAUTH_ACCESS_TOKEN_URL
import com.radionov.githubclient.data.datasource.local.CacheManager
import com.radionov.githubclient.data.datasource.network.GithubApi
import com.radionov.githubclient.data.datasource.network.GithubAuthApi
import com.radionov.githubclient.utils.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.utils.RxComposers
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.scalars.ScalarsConverterFactory




/**
 * @author Andrey Radionov
 */
@Module
class NetworkModule {

    @NonNull
    @Provides
    @Singleton
    fun provideGithubApi(httpClient: OkHttpClient): GithubApi {

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(GithubApi::class.java)
    }

    @NonNull
    @Provides
    @Singleton
    fun provideGithubAuthApi(): GithubAuthApi {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(OAUTH_ACCESS_TOKEN_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubAuthApi::class.java)
    }

    @NonNull
    @Provides
    @Singleton
    fun provideCacheManager(app: Application, rxComposers: RxComposers) = CacheManager(app, rxComposers)

    @NonNull
    @Provides
    @Singleton
    fun provideOkHttp(app: Application, networkManager: NetworkManager, prefs: Prefs): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(initAuthInterceptor(prefs))
            .addInterceptor(initOfflineCacheInterceptor(networkManager))
            .addNetworkInterceptor(initCacheInterceptor())
            .cache(initCache(app))
            .build()
    }

    @NonNull
    @Provides
    @Singleton
    fun provideNetworkManager(app: Application) =
        NetworkManager(app.applicationContext)


    private fun initCache(app: Application): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(app.cacheDir, "http-cache"), (CACHE_SIZE).toLong())
        } catch (e: Exception) {
            Log.e(TAG, "Could not create Cache!")
        }

        return cache
    }

    private fun initCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            // re-write response header to force use of cache
            val cacheControl = CacheControl.Builder()
                .maxAge(MAX_AGE, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .removeHeader(PRAGMA_HEADER)
                .removeHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER)
                .removeHeader(VARY_HEADER)
                .removeHeader(CACHE_CONTROL_HEADER)
                .header(CACHE_CONTROL_HEADER, cacheControl.toString())
                .build()
        }
    }

    private fun initOfflineCacheInterceptor(networkManager: NetworkManager): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            if (!networkManager.isInternetAvailable()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(STALE_TIME, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }

            chain.proceed(request)
        }
    }

    private fun initAuthInterceptor(prefs: Prefs): Interceptor {
        val apiToken = "token " + prefs.getToken()

        return Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .header("Authorization", apiToken)
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }
    }

    companion object {
        private const val TAG = "AppModule"
        private const val CACHE_CONTROL_HEADER = "Cache-Control"
        private const val PRAGMA_HEADER = "Pragma"
        private const val ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin"
        private const val VARY_HEADER = "Vary"
        private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
        private const val MAX_AGE = 10
        private const val STALE_TIME = 7
    }
}
