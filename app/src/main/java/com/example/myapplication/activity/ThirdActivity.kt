package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ThirdLayoutBinding

class ThirdActivity : BaseActivity() {

    private lateinit var binding: ThirdLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("ThirdActivity", "Task id is $taskId")
        binding = ThirdLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toDatabase.setOnClickListener {
            val intent = Intent(this, DataBase::class.java)
            startActivity(intent)
        }
        binding.send.setOnClickListener{
            Toast.makeText(this, "射不出来", Toast.LENGTH_SHORT).show()
        }
        binding.toService.setOnClickListener {
            val intent = Intent(this, TryService::class.java)
            startActivity(intent)
        }

    }
}