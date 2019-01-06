package com.radionov.githubclient.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.radionov.githubclient.viewmodels.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * @author Andrey Radionov
 */
abstract class BaseActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModeFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}