package com.radionov.githubclient.data.datasource.local

import android.content.Context
import com.radionov.githubclient.utils.RxComposers
import io.reactivex.Completable
import java.io.File

/**
 * @author Andrey Radionov
 */
class CacheManager(private val context: Context,
                   private val rxComposers: RxComposers) {

    fun clearWebViewCachesCustom() {
        val dataDirName = context.packageManager.getPackageInfo(context.packageName, 0).applicationInfo.dataDir
        val dataDir = File("$dataDirName/app_webview/")
        Completable
            .fromAction { deleteDirectory(dataDir) }
            .compose(rxComposers.getCompletableComposer())
            .subscribe()
    }

    private fun deleteDirectory(directory: File): Boolean {
        if (directory.exists()) {
            val files = directory.listFiles()
            if (null != files) {
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        deleteDirectory(files[i])
                    } else {
                        files[i].delete()
                    }
                }
            }
        }
        return directory.delete()
    }
}
