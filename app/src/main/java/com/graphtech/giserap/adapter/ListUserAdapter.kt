package com.graphtech.giserap.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.activity.DetailUserActivity
import com.graphtech.giserap.model.User
import kotlinx.android.synthetic.main.item_user.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ListUserAdapter(val context: Context, val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]

        holder.tvName.text = user.name
        holder.tvCompany.text = user.company
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.imgUser)
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.imgBackground)

        holder.cardIntent.setOnClickListener {
            val intent = Intent(context, DetailUserActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelable("user", user)
            intent.putExtra("detail_user", bundle)
            context.startActivity(intent)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.tvItemName
        var tvCompany = itemView.tvItemCompany
        var imgUser = itemView.imgItemUser
        var imgBackground = itemView.imgItemBackground
        var cardIntent = itemView.intentDetail
    }
}