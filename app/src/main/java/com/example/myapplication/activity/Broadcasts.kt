package com.example.myapplication.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R


class Broadcasts : BaseActivity() {

    lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_broadcasts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sendBroadcasts : Button = findViewById(R.id.sendBroadcast)
        val forceOffline : Button = findViewById(R.id.forceOffline)
        sendBroadcasts.setOnClickListener {
            val intent = Intent("com.example.myapplication.MY_BROADCAST")
            intent.setPackage(packageName)
            sendOrderedBroadcast(intent, null)
        }

        forceOffline.setOnClickListener {
            val intent = Intent("com.example.myapplication.FORCE_OFFLINE")
            sendBroadcast(intent)
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver((timeChangeReceiver))
    }



    inner class TimeChangeReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "时间变了", Toast.LENGTH_LONG).show()
        }
    }

}