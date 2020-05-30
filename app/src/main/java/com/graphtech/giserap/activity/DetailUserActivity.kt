package com.graphtech.giserap.activity

import android.content.ContentValues
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
import com.graphtech.giserap.model.User
import com.graphtech.giserap.presenter.DetailUsernamePresenter
import com.graphtech.giserap.view.DetailUsernameView
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.toast


class DetailUserActivity : AppCompatActivity(), DetailUsernameView {

    private lateinit var detailUsernamePresenter: DetailUsernamePresenter
    private lateinit var favoriteHelper: FavoriteHelper
    private var favorite: Favorite? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        // get bundle data from parcelable
        val bundle = intent.getBundleExtra("detail_user")
        val user = bundle.getParcelable("user") as? User

        // fav sqlite
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        favorite = Favorite()

        // declare
        detailUsernamePresenter = DetailUsernamePresenter(this)
        detailUsernamePresenter.getDetailUsername(user?.username)

        // setup actionbar
        setupNavigation()

        // check isFavorite
        favoriteState(user?.username.toString())
        setFavorite()

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
        btnFavoriteUser.hide()
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
        btnFavoriteUser.show()
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
                removeFromFavorite(response?.login.toString())
            } else {
                addToFavorite(response?.login.toString(), response?.avatarUrl.toString())
            }
            isFavorite = !isFavorite
            setFavorite()
        }
    }

    override fun onErrorGetDetailUsername(message: String) {

    }

    private fun favoriteState(username: String) {
        val check = favoriteHelper.queryById(username)
        val result = check.parseList(classParser<Favorite>())
        if (result.isNotEmpty()) isFavorite = true
    }

    private fun setFavorite() {
        if (isFavorite) {
            btnFavoriteUser.setImageResource(R.drawable.ic_added_favorite)
        } else {
            btnFavoriteUser.setImageResource(R.drawable.ic_add_favorite)
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
