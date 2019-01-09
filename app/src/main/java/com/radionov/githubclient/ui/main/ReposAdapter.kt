package com.radionov.githubclient.ui.main

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radionov.githubclient.R
import com.radionov.githubclient.data.entity.Repository
import com.radionov.githubclient.utils.ReposDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    private val differ = AsyncListDiffer(this, ReposDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)

        return ReposViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(newItems: List<Repository>) {
        differ.submitList(newItems)
    }

    inner class ReposViewHolder internal constructor(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(repository: Repository) {
            itemView.tv_author_name.text = repository.owner.login
            itemView.tv_repo_name.text = repository.name
            val license = repository.license?.key ?: "None"
            itemView.tv_repo_license.text = license
            Picasso.get()
                .load(repository.owner.avatarUrl)
                .placeholder(R.drawable.ic_person_black)
                .into(itemView.iv_author_image)
            itemView.setOnClickListener { listener.onClick(repository) }
        }
    }

    interface OnItemClickListener {
        fun onClick(repository: Repository)
    }
}
