package com.radionov.githubclient.ui

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import okhttp3.*

import java.io.IOException

import com.radionov.githubclient.BuildConfig.*

class OAuthActivity : BaseActivity() {

    private var tokenAsync: TokenAsync? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = "$OAUTH_URL?client_id=$CLIENT_ID"

        val webview = WebView(this)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                val codeFragment = "code="

                if (url.contains(codeFragment)) {
                    // the GET request contains an authorization code
                    val accessCode = url.substring(url.indexOf(codeFragment) + codeFragment.length)
                    Log.d(TAG, "accessCode=$accessCode")
                    //стопаем иначе редирект на callbackUrl
                    webview.stopLoading()

                    tokenAsync = TokenAsync()
                    tokenAsync!!.execute(accessCode)

                }
            }


        }
        webview.loadUrl(url)
        setContentView(webview)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tokenAsync != null && !tokenAsync!!.isCancelled) {
            tokenAsync!!.cancel(true)
        }
    }

    private class TokenAsync : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg params: String): Void? {
            val client = OkHttpClient()

            val formBody = FormBody.Builder()
                .add("client_id", CLIENT_ID)
                .add("client_secret", CLIENT_SECRET)
                .add("code", params[0])
                .build()
            val request = Request.Builder().url(OAUTH_ACCESS_TOKEN_URL).post(formBody).build()
            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Log.d(TAG, "Unexpected code $response")
                } else {
                    //Сохранить в префы и выйти
                    Log.d(TAG, "token" + response.body()!!.string())
                }
            } catch (e: IOException) {
                Log.d(TAG, "IOEXc")
            }

            return null
        }
    }

    companion object {
        var TAG = "OAuthActivity"
    }
}