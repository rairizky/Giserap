package com.graphtech.giserap.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.DetailPagerAdapter
import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.model.User
import com.graphtech.giserap.presenter.DetailUsernamePresenter
import com.graphtech.giserap.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_detail_user.*


class DetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        // get bundle data from parcelable
        val bundle = intent.getBundleExtra("detail_user")
        val user = bundle.getParcelable<User?>("user") as User

        // declare
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(user.username)

        // setup actionbar
        setupNavigation()

        // setup actionBar
        setupActionBar(user.username)

        // back button
        intentMainActivity.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun setupActionBar(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, username)
        vpDetail.adapter = detailPagerAdapter
        tlDetail.setupWithViewPager(vpDetail)
    }

    override fun onShowLoading() {
        shimmerDetailLocation.visibility = View.VISIBLE
        shimmerDetailName.visibility = View.VISIBLE
        shimmerDetailUsername.visibility = View.VISIBLE
        shimmerDetailLocation.startShimmer()
        shimmerDetailName.startShimmer()
        shimmerDetailUsername.startShimmer()
        gridMenuSocial.visibility = View.GONE
        linearLocation.visibility = View.GONE
    }

    override fun onHideLoading() {
        shimmerDetailLocation.visibility = View.GONE
        shimmerDetailName.visibility = View.GONE
        shimmerDetailUsername.visibility = View.GONE
        shimmerDetailLocation.stopShimmer()
        shimmerDetailName.stopShimmer()
        shimmerDetailUsername.stopShimmer()
        gridMenuSocial.visibility = View.VISIBLE
        linearLocation.visibility = View.VISIBLE
    }

    override fun onSuccessGetDetailUsername(response: DetailUserResponse?) {
        Glide.with(this)
            .load(response?.avatarUrl)
            .into(imageDetailUserProfile)
        tvDetailUserName.text = response?.name
        tvDetailUserCompany.text = response?.company
        tvDetailUserUsername.text = response?.login
        tvDetailUserLocation.text = response?.location
        tvDetailUserRepo.text = response?.publicRepos.toString()
        tvDetailUserFollower.text = response?.followers.toString()
        tvDetailUserFollowing.text = response?.following.toString()
    }

    override fun onErrorGetDetailUsername(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
