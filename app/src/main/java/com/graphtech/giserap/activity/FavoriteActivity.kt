package com.graphtech.giserap.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.FavoriteUserAdapter
import com.graphtech.giserap.helper.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.graphtech.giserap.helper.FavoriteHelper
import com.graphtech.giserap.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.jetbrains.anko.toast

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // setup action bar
        setupNavigation()

        // back to main
        intentMainActivity.setOnClickListener {
            finish()
        }

        // rv
        //rvFavorite.layoutManager = LinearLayoutManager(applicationContext)
        adapter = FavoriteUserAdapter(this)
        //rvFavorite.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            // shimmer visible here
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            // shimmer gone here
            val favorites = deferredFavorite.await()
            if (favorites.size > 0) {
                adapter.notifyDataSetChanged()
                rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
                rvFavorite.adapter = adapter
                adapter.listFavorite = favorites
            } else {
                adapter.listFavorite = ArrayList()
                toast("No Favorite here")
            }
        }
    }

}
