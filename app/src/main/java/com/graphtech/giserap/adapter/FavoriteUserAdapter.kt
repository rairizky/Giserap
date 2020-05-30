package com.graphtech.giserap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.activity.SearchDetailUserActivity
import com.graphtech.giserap.model.Favorite
import kotlinx.android.synthetic.main.item_user.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class FavoriteUserAdapter (private val context: Context) : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    fun addItem(favorite: Favorite) {
        this.listFavorite.add(favorite)
        notifyItemInserted(this.listFavorite.size - 1)
    }

    fun removeItem(position: Int) {
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorite.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int = this.listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val fav = listFavorite[position]

        holder.tvUsername.text = fav.username
        Glide.with(holder.itemView.context)
            .load(fav.avatar)
            .into(holder.imgAvatar)
        Glide.with(holder.itemView.context)
            .load(fav.avatar)
            .into(holder.bgAvatar)
        holder.itemFav.setOnClickListener {
            context.startActivity<SearchDetailUserActivity>("username" to fav.username)
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemFav = itemView.intentDetail
        var tvUsername = itemView.tvItemName
        var imgAvatar = itemView.imgItemUser
        var bgAvatar = itemView.imgItemBackground
    }
}