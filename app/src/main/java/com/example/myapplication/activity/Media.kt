package com.example.myapplication.activity

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.exifinterface.media.ExifInterface
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNotificationBinding
import java.io.File

class Media : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val manager2 = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("normal", "Normal", NotificationManager.
            IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
            val channel2 = NotificationChannel("important", "Important", NotificationManager.
            IMPORTANCE_HIGH) // 让通知变得更重要
            manager2.createNotificationChannel(channel2)

        }
        binding.sendNotice.setOnClickListener {
            val intent = Intent(this, ListView::class.java)
            val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)// Android13啊Android13

            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //Android13你怎么回事
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            }

            val notification = NotificationCompat.Builder(this, "normal")
                .setContentTitle("好好好")
                .setStyle(NotificationCompat.BigTextStyle().bigText("去看看低性能的水果吧，可是没有原神玩我要死了啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊求求你给我玩原神吧施瓦罗先生"))
                // 让长长文字也能在通知栏里边显示完全（原本是setContentText的
                .setSmallIcon(R.drawable.apple)
                .setLargeIcon(BitmapFactory.decodeResource(resources,
                R.drawable.elaina))
                .setContentIntent(pi)
                .setAutoCancel(true) //点了自动消失
                .build()
            manager.notify(1, notification) // 所以id是1哦

            val notification1 = NotificationCompat.Builder(this, "normal").
            setContentTitle("好好好")
                .setSmallIcon(R.drawable.apple)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(
                    BitmapFactory.decodeResource(resources, R.drawable.elaina)
                )) // 放出大大图片
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
            manager.notify(2, notification1)

        }

        binding.takePhotoBtn.setOnClickListener {
            // 创一个File对象哈哈哈
            outputImage = File(externalCacheDir, "output_image.png") // 我就要png
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = FileProvider.getUriForFile(this, "com.example.myapplication.fileprovider", outputImage)

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        binding.fromAlbumBtn.setOnClickListener {
            // 打开，让我访问！
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 让我访问图片！
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }

        binding.toPlay.setOnClickListener {
            val intent = Intent(this, Playing::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            takePhoto -> {
                if (resultCode == RESULT_OK) {
                    // 显示照片
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    binding.imageView.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum -> {
                if (resultCode == RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        // 显示图片捏
                        val bitmap = getBitmapFromUri(uri)
                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height,
            matrix, true)
        bitmap.recycle() // 回收不要的bitmap对象
        return rotateBitmap
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }
}