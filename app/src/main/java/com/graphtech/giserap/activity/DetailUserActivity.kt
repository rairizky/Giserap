package com.graphtech.giserap.activity

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.DetailPagerAdapter
import com.graphtech.giserap.helper.database
import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.model.Favorite
import com.graphtech.giserap.model.User
import com.graphtech.giserap.presenter.DetailUsernamePresenter
import com.graphtech.giserap.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast


class DetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        // get bundle data from parcelable
        val bundle = intent.getBundleExtra("detail_user")
        val user = bundle.getParcelable("user") as? User

        // check fav button
        favoriteState(user?.username.toString())
        setFavorite()

        // declare
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(user?.username)

        // setup actionbar
        setupNavigation()

        // setup actionBar
        setupActionBar(user?.username.toString())

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

        btnFavoriteUser.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite("${response?.login}")
            }else {
                addToFavorite("${response?.login}", "${response?.avatarUrl}")
            }
            isFavorite = !isFavorite
            setFavorite()
        }
    }

    override fun onErrorGetDetailUsername(message: String) {

    }

    private fun favoriteState(id: String) {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(USER_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            btnFavoriteUser.setImageResource(R.drawable.ic_added_favorite)
        else
            btnFavoriteUser.setImageResource(R.drawable.ic_add_favorite)
    }

    private fun addToFavorite(username: String, avatar: String) {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.USER_ID to username,
                    Favorite.USER_AVATAR to avatar)
            }
            toast("Added to favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(username: String) {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(USER_ID = {id})",
                    "id" to username)
            }
            toast("Removed from favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }
}
