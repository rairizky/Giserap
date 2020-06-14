package com.graphtech.giserap.activity

import android.content.ContentValues
import android.database.SQLException
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.DetailPagerAdapter
import com.graphtech.giserap.helper.DatabaseContract
import com.graphtech.giserap.helper.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.graphtech.giserap.helper.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.graphtech.giserap.helper.FavoriteHelper
import com.graphtech.giserap.helper.MappingHelper
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
    private var favorite: Favorite? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail_user)

        // getString
        val username = intent.getStringExtra("username") as String

        // setup actionBar
        setupActionBar()

        // setup viewPager
        setupViewPager(username)

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

        isFavorite = favoriteState(response?.id?.toLong())
        setFavorite()

        btnFavoriteSearchUser.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite(response?.id?.toLong())
            } else {
                addToFavorite(response?.login.toString(), response?.avatarUrl.toString(), response?.id?.toLong())
            }
            isFavorite = !isFavorite
            setFavorite()
        }
    }

    override fun onErrorGetDetailUsername(message: String) {
        toast(message)
    }

    private fun favoriteState(userId: Long?) : Boolean {
       val uri = Uri.parse("$CONTENT_URI/$userId")
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.count != 0) {
            favorite = MappingHelper.mapCursorToObject(cursor)
            cursor.close()
            return true
        }
        return false
    }

    private fun setFavorite() {
        if (isFavorite) {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_added_favorite)
        } else {
            btnFavoriteSearchUser.setImageResource(R.drawable.ic_add_favorite)
        }
    }

    private fun addToFavorite(username: String, avatar: String, userId: Long?) {
        val values = ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME, username)
        values.put(DatabaseContract.FavoriteColumns.AVATAR, avatar)
        values.put(DatabaseContract.FavoriteColumns._ID, userId)

        contentResolver.insert(CONTENT_URI, values)
        toast("Added to favorite")
    }

    private fun removeFromFavorite(userId: Long?) {
        val uriWithId = Uri.parse("$CONTENT_URI/$userId")
        contentResolver.delete(uriWithId, null, null)
        toast("Removed from favorite")
    }
}
