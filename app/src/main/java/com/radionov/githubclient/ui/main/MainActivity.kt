package com.radionov.githubclient.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.radionov.githubclient.R
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.ui.auth.OAuthActivity
import com.radionov.githubclient.ui.common.BaseActivity
import com.radionov.githubclient.ui.details.DetailsActivity
import com.radionov.githubclient.ui.details.REPO_EXTRA
import com.radionov.githubclient.utils.AuthStates
import com.radionov.githubclient.utils.Responses
import com.radionov.githubclient.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var reposAdapter: ReposAdapter
    private val clickListener = object: ReposAdapter.OnItemClickListener {
        override fun onClick(repository: Repository) {
            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
            intent.putExtra(REPO_EXTRA, repository)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_signout -> {
                viewModel.signOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
            .of(this, viewModeFactory)
            .get(MainViewModel::class.java)

        viewModel.subscribeAuthState()
            .observe(this, Observer<AuthStates> { state ->
                if (state == AuthStates.LOGGED) {
                    init()
                } else {
                    val intent = Intent(this, OAuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            })
    }

    private fun init() {
        reposAdapter = ReposAdapter(clickListener)

        repos_container.layoutManager = LinearLayoutManager(this)
        repos_container.adapter = reposAdapter

        swipe_container.setOnRefreshListener { viewModel.getRepositories() }
        btn_load.setOnClickListener {
            swipe_container.isRefreshing = true
            viewModel.getRepositories()
        }

        viewModel.subscribeRepos()
            .observe(this, Observer { response ->
                response?.let {
                    swipe_container.isRefreshing = false
                    if (it.first == Responses.FAIL) {
                        setVisibility(empty = View.VISIBLE)
                    } else {
                        setVisibility(container = View.VISIBLE)
                        reposAdapter.setData(it.second)
                    }
                }
            })

        viewModel.getRepositories()
    }

    private fun setVisibility(empty: Int = View.INVISIBLE, container: Int = View.INVISIBLE) {
        tv_empty.visibility = empty
        btn_load.visibility = empty
        repos_container.visibility = container
    }
}
