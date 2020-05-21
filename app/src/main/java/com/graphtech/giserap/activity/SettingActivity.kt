package com.graphtech.giserap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graphtech.giserap.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        
        // setup actionBar
        setupNavigation()

        // intent main
        intentMainActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
