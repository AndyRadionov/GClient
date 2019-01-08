package com.radionov.githubclient.utils

import android.support.v7.util.DiffUtil
import com.radionov.githubclient.data.entity.Repository

/**
 * @author Andrey Radionov
 */
class ReposDiffCallback : DiffUtil.ItemCallback<Repository>() {

    override fun areItemsTheSame(repo1: Repository, repo2: Repository) = repo1.id == repo2.id

    override fun areContentsTheSame(repo1: Repository, repo2: Repository) = repo1 == repo2
}
