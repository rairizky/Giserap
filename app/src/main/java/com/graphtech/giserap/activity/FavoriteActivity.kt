package com.graphtech.giserap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.FavoriteUserAdapter
import com.graphtech.giserap.helper.database
import com.graphtech.giserap.model.Favorite
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoriteActivity : AppCompatActivity() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var favoriteAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // setup action bar
        setupNavigation()

        // back to main
        intentMainActivity.setOnClickListener {
            finish()
        }
        rvFavorite.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteUserAdapter(favorites)
        rvFavorite.adapter = favoriteAdapter
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun showFavorite() {
        favorites.clear()
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            favoriteAdapter.notifyDataSetChanged()
        }
    }
}
