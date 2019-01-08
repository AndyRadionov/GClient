package com.radionov.githubclient.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.radionov.githubclient.R
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.ui.common.BaseActivity
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.viewmodels.DetailsViewModel

const val REPO_EXTRA = "repo_extra"

class DetailsActivity : BaseActivity() {

    private lateinit var repo: Repository
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repo = intent.getParcelableExtra(REPO_EXTRA)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModeFactory)
            .get(DetailsViewModel::class.java)

        viewModel.subscribeCommit()
            .observe(this, Observer { response ->
                response?.let {
                    if (it.first == Responses.FAIL) {
                        Toast.makeText(this@DetailsActivity, "Fail", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@DetailsActivity, it.second?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        //viewModel.getLastCommit(repo.owner?.login, repo.name)
    }
}
