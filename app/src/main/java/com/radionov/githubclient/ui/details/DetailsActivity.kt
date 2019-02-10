package com.radionov.githubclient.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.radionov.githubclient.R
import com.radionov.githubclient.data.entity.CommitResponse
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.ui.common.BaseActivity
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

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
                        Toast.makeText(this@DetailsActivity, getString(R.string.msg_fail), Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        setViews(it.second as CommitResponse)
                    }
                }
            })

        viewModel.getLastCommit(repo.owner.login, repo.name)
    }

    private fun setViews(commit: CommitResponse) {
        pb_loading.visibility = View.INVISIBLE
        tv_repo_name.text = repo.fullName
        tv_watchers.text = repo.watchersCount.toString()
        tv_stars.text = repo.starsCount.toString()
        tv_forks.text = repo.forksCount.toString()
        repo.description?.let { tv_description.text = it }
        tv_commit_hash.text = commit.sha.substring(0, SHA_LENGTH)
        tv_commit_msg.text = commit.commit.message
        tv_commit_author.text = commit.commit.author.toString()
    }

    companion object {
        private const val SHA_LENGTH = 8
    }
}
