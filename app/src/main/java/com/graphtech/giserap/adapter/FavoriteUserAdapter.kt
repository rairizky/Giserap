package com.graphtech.giserap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.activity.DetailUserActivity
import com.graphtech.giserap.activity.SearchDetailUserActivity
import com.graphtech.giserap.model.Favorite
import kotlinx.android.synthetic.main.item_user.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class FavoriteUserAdapter(private val favorite: List<Favorite>)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount() : Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position])
    }
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val userBackground = view.imgItemBackground
    private val userAvatar = view.imgItemUser
    private val userUsername = view.tvItemName
    private val intentDetail = view.intentDetail

    fun bindItem(favorite: Favorite) {
        Glide.with(itemView.context)
            .load(favorite.userAvatar)
            .into(userBackground)
        Glide.with(itemView.context)
            .load(favorite.userAvatar)
            .into(userAvatar)
        userUsername.text = favorite.userId

        intentDetail.setOnClickListener {
            itemView.context.startActivity<SearchDetailUserActivity>("username" to favorite.userId)
        }
    }
}