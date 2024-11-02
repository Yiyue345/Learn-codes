package com.example.myapplication.lei

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "收到广播啦！", Toast.LENGTH_SHORT).show()
        abortBroadcast()
    }
}