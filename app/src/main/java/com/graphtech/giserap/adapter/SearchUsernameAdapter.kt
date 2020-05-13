package com.graphtech.giserap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.activity.SearchDetailUserActivity
import com.graphtech.giserap.model.SearchItem
import kotlinx.android.synthetic.main.item_search_user.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchUsernameAdapter(private val context: Context?, var data: List<SearchItem?>?) : RecyclerView.Adapter<SearchUsernameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = data?.get(position)

        holder.tvUsername.text = searchResult?.login
        Glide.with(holder.itemView.context)
            .load(searchResult?.avatarUrl)
            .into(holder.imgUser)
        Glide.with(holder.itemView.context)
            .load(searchResult?.avatarUrl)
            .into(holder.imgBackground)
        holder.cardIntent.setOnClickListener {
            context?.startActivity<SearchDetailUserActivity>("username" to searchResult?.login)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername = itemView.tvItemSearchUsername
        var imgUser = itemView.imgItemSearchUser
        var imgBackground = itemView.imgItemSearchBackground
        var cardIntent = itemView.intentSearchDetail
    }
}