package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.myapplication.activity.TryService

class MyService : Service() {

    private val  mBinder = DownloadBinder()

    class DownloadBinder : Binder() {

        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }
        fun getProgress(): Int {
            Log.d("MyService", "getProgress executed")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() { // 仅创建时执行
        super.onCreate()
        Log.d("MyService", "onCreate executed")


        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("my_service", "前台service哦",
                NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(this, TryService::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("我是标题！").setContentText("Ciallo!").setSmallIcon(R.drawable.no)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.elaina))
            .setContentIntent(pi)
            .build()
        startForeground(3, notification) // 去声明合适的类型与权限！
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { // 创建之后还能执行
        Log.d("MyService", "onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }

}