package com.graphtech.giserap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graphtech.giserap.R
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // setup action bar
        setupNavigation()

        // back to main
        intentMainActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
