package com.radionov.githubclient.ui.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.radionov.githubclient.BuildConfig.CLIENT_ID
import com.radionov.githubclient.BuildConfig.OAUTH_URL
import com.radionov.githubclient.ui.common.BaseActivity
import com.radionov.githubclient.ui.main.MainActivity
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.viewmodels.AuthViewModel

class OAuthActivity : BaseActivity() {

    private lateinit var viewModel: AuthViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        val webview = WebView(this)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                val codeFragment = "code="

                if (url.contains(codeFragment)) {
                    // the GET request contains an authorization code
                    val accessCode = url.substring(url.indexOf(codeFragment) + codeFragment.length)
                    //stop loading else redirects to callbackUrl
                    webview.stopLoading()

                    viewModel.getToken(accessCode)
                }
            }
        }
        webview.loadUrl(URL)
        setContentView(webview)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModeFactory)
            .get(AuthViewModel::class.java)

        viewModel.subscribeResponse()
            .observe(this, Observer<Responses> { response ->
                if (response == Responses.FAIL) {
                    Toast.makeText(this@OAuthActivity, "Fail", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            })
    }

    companion object {
        var TAG = "OAuthActivity"
        private const val URL = "$OAUTH_URL?client_id=$CLIENT_ID"
    }
}