package com.graphtech.giserap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graphtech.giserap.R
import org.jetbrains.anko.toast

class SearchDetailUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail_user)

        // setup actionBar
        setupActionBar()
        
        // getString
        val username = intent.getStringExtra("username") as String
        toast(username)
    }

    private fun setupActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
