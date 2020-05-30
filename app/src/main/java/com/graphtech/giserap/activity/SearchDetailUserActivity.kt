package com.graphtech.giserap.activity

import android.content.ContentValues
import android.database.SQLException
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.DetailPagerAdapter
import com.graphtech.giserap.helper.DatabaseContract
import com.graphtech.giserap.helper.FavoriteHelper
import com.graphtech.giserap.model.DetailUserResponse
import com.graphtech.giserap.model.Favorite
import com.graphtech.giserap.presenter.DetailUsernamePresenter
import com.graphtech.giserap.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_search_detail_user.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.toast


class SearchDetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter
    private lateinit var favoriteHelper: FavoriteHelper
    private var favorite: Favorite? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail_user)

        // getString
        val username = intent.getStringExtra("username") as String

        // setup actionBar
        setupActionBar()

        // sqlite
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favorite = Favorite()

        // setup viewPager
        setupViewPager(username)

        // check isFavorite
        favoriteState(username)
        setFavorite()

        // declare presenter
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(username)

        // back button
        intentSearchActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun setupViewPager(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, username)
        vpSearchDetail.adapter = detailPagerAdapter
        tlSearchDetail.setupWithViewPager(vpSearchDetail)
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
        btnFavoriteSearchUser.hide()
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
        btnFavoriteSearchUser.show()
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

        btnFavoriteSearchUser.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite(response?.login.toString())
            } else {
                addToFavorite(response?.login.toString(), response?.avatarUrl.toString())
            }
            isFavorite = !isFavorite
            setFavorite()
        }
    }

    override fun onErrorGetDetailUsername(message: String) {
        toast(message)
    }

    private fun favoriteState(username: String) {
        val check = favoriteHelper.queryById(username)
        val result = check.parseList(classParser<Favorite>())
        if (result.isNotEmpty()) isFavorite = true
    }

    private fun setFavorite() {
        if (isFavorite) {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_added_favorite)
        } else {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_add_favorite)
        }
    }

    private fun addToFavorite(username: String, avatar: String) {
        val values = ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME, username)
        values.put(DatabaseContract.FavoriteColumns.AVATAR, avatar)

        val result = favoriteHelper.insert(values)
        if (result > 0) {
            toast("Added to favorite")
        } else {
            toast("Can't add to favorite!")
        }
    }

    private fun removeFromFavorite(username: String) {
        val result = favoriteHelper.deleteById(username)
        if (result > 0) {
            toast("Removed from favorite")
        }
    }
}
