package com.graphtech.consumerapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphtech.consumerapp.R
import com.graphtech.consumerapp.adapter.FavoriteUserAdapter
import com.graphtech.consumerapp.helper.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.graphtech.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up actionBar
        setupNavigation()

        adapter = FavoriteUserAdapter(this)
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
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
                rvFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
                rvFavorite.adapter = adapter
                adapter.listFavorite = favorites
            } else {
                adapter.listFavorite = ArrayList()
                toast("No Favorite here")
            }
        }
    }
}
