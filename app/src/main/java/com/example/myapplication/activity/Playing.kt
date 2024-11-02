package com.example.myapplication.activity

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityPlayingBinding

class Playing : BaseActivity() {

    private lateinit var binding: ActivityPlayingBinding
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val uri = Uri.parse("android.resource://$packageName/${R.raw.history}")
        binding.videoView.setVideoURI(uri)
        initMediaPlayer()
        binding.play.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }
        binding.pause.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
        }
        binding.stop.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.reset()
                initMediaPlayer()
            }
        }

        binding.playVideo.setOnClickListener {
            if (!binding.videoView.isPlaying){
                binding.videoView.start()
            }
        }
        binding.pauseVideo.setOnClickListener {
            if (binding.videoView.isPlaying) {
                binding.videoView.pause()
            }
        }
        binding.stopVideo.setOnClickListener {
            if (!binding.videoView.isPlaying){
                binding.videoView.resume()
            }
        }


    }

    private fun initMediaPlayer(){
        val assetManager = assets
        val fd = assetManager.openFd("塞壬唱片-MSR,Eagle Wei - 海愿.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.suspend()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}