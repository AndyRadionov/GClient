package com.radionov.githubclient.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.radionov.githubclient.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onAuthClick(v: View) {
        startActivity(Intent(this, OAuthActivity::class.java))
    }
}
