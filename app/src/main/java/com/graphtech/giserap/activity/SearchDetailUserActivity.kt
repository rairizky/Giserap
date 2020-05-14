package com.graphtech.giserap.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.graphtech.giserap.R
import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.presenter.DetailUsernamePresenter
import com.graphtech.giserap.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_search_detail_user.*
import org.jetbrains.anko.toast
import java.io.Reader


class SearchDetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail_user)

        // setup actionBar
        setupActionBar()
        
        // getString
        val username = intent.getStringExtra("username") as String

        // declare presenter
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(username)

        intentSearchActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    override fun onShowLoading() {
        shimmerDetailLocation.visibility = View.VISIBLE
        shimmerDetailName.visibility = View.VISIBLE
        shimmerDetailUsername.visibility = View.VISIBLE
        shimmerDetailLocation.startShimmer()
        shimmerDetailName.startShimmer()
        shimmerDetailUsername.startShimmer()
        gridMenuSocial.visibility = View.GONE
    }

    override fun onHideLoading() {
        shimmerDetailLocation.visibility = View.GONE
        shimmerDetailName.visibility = View.GONE
        shimmerDetailUsername.visibility = View.GONE
        shimmerDetailLocation.stopShimmer()
        shimmerDetailName.stopShimmer()
        shimmerDetailUsername.stopShimmer()
        gridMenuSocial.visibility = View.VISIBLE
    }

    override fun onSuccessGetDetailUsername(response: DetailUserResponse?) {
        tvDetailUserSearchLocation.text = response?.location
        Glide.with(this)
            .load(response?.avatarUrl)
            .into(imageDetailUserSearchProfile)
        tvDetailUserSearchName.text = response?.name
        tvDetailUserSearchCompany.text = response?.company
        tvDetailUserSearchUsername.text = response?.login
        tvDetailUserSearchRepo.text = response?.publicRepos.toString()
        tvDetailUserSearchFollower.text = response?.followers.toString()
        tvDetailUserSearchFollowing.text = response?.following.toString()
    }

    override fun onErrorGetDetailUsername(message: String) {
        toast(message)
    }
}
