package com.graphtech.giserap.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import com.graphtech.giserap.R
import com.graphtech.giserap.broadcast.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.toast

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        alarmReceiver = AlarmReceiver()

        // setup actionBar
        setupNavigation()

        // intent main
        intentMainActivity.setOnClickListener {
            finish()
        }

        // setting alarm notification
        setAlarmNotification()
    }

    private fun setAlarmNotification() {
        // on alarm
        btnOnAlarm.setOnClickListener {
            val message = "Hello there, check app now!"
            alarmReceiver.setRepeatingAlarm(this, message)
        }

        // off alarm
        btnOffAlarm.setOnClickListener {
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }
}
