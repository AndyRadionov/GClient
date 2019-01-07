package com.radionov.githubclient.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.radionov.githubclient.R
import com.radionov.githubclient.ui.auth.OAuthActivity
import com.radionov.githubclient.ui.common.BaseActivity
import com.radionov.githubclient.utils.AuthStates
import com.radionov.githubclient.viewmodels.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    fun onAuthClick(v: View) {
        startActivity(Intent(this, OAuthActivity::class.java))
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModeFactory)
            .get(MainViewModel::class.java)

        viewModel.subscribeAuthState()
            .observe(this, Observer<AuthStates> { state ->
                if (state == AuthStates.LOGGED) {
                    //init()
                } else {
                    //start OAUTH
                }
            })
    }

}
