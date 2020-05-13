package com.graphtech.giserap.activity

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.graphtech.giserap.R
import com.graphtech.giserap.model.User
import kotlinx.android.synthetic.main.activity_detail_user.*


class DetailUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        // get bundle data from parcelable
        val bundle = intent.getBundleExtra("detail_user")
        val user = bundle.getParcelable<User?>("user") as User

        // insert data to item
        setData(user)

        // setup actionbar
        setupNavigation()

        // back button
        intentMainActivity.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    private fun setData(user: User) {
        // proccess image profile
        Glide.with(this)
            .load(user.avatar)
            .into(imageDetailUserProfile)
        tvDetailUserName.text = user.name
        tvDetailUserCompany.text = user.company
        imageDetailUserBackground.setImageSource(user.avatar)
        tvDetailUserUsername.text = user.username
        tvDetailUserLocation.text = user.location
        tvDetailUserRepo.text = user.repository
        tvDetailUserFollower.text = user.follower
        tvDetailUserFollowing.text = user.following
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
