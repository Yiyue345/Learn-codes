package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.SecondSecondBinding
import com.example.myapplication.showToast


class SecondActivity : BaseActivity() {

    private lateinit var binding: SecondSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SecondSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val extraData = intent.getStringExtra("extra_data")
//        Log.d("SecondActivity", "extra data is $extraData")
        Log.d("SecondActivity", "Task id is $taskId")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button2.setOnClickListener{
            Toast.makeText(this, "OK? NO!", Toast.LENGTH_SHORT).show()
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:12345678910")
//            startActivity(intent)

//            intent.putExtra("data_return", "Hello第一个")
//            setResult(RESULT_OK, intent)
//            finish()
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
        binding.button233.setOnClickListener{
            binding.progressBar.progress += 10
            binding.imageView.setImageResource(R.drawable.loong)
//            Toast.makeText(this, "啊我变了", Toast.LENGTH_SHORT).show()
            "啊我变了".showToast(this) // 自己写，简化更方便！
            if (binding.progressBar.progress >= 100){
                binding.progressBar.visibility = View.GONE
                binding.imageView.setImageResource(R.drawable.home_image)
//                Toast.makeText(this, "啊我又变了", Toast.LENGTH_SHORT).show()
                "啊我又变了".showToast(this)
            }
//            if (progressBar.visibility == View.VISIBLE) {
//                progressBar.visibility = View.GONE
//                imageView.setImageResource(R.drawable.loong)
//                Toast.makeText(this, "啊我变了", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                progressBar.visibility = View.VISIBLE
//                imageView.setImageResource(R.drawable.home_image)
//                Toast.makeText(this, "啊我又变了", Toast.LENGTH_SHORT).show()
//            }



        }

        binding.toBroadcasts.setOnClickListener {
            val intent = Intent(this, Broadcasts::class.java)
            startActivity(intent)
        }

        binding.toNotification.setOnClickListener {
            val intent = Intent(this, Media::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondActivity", "onDestroy")
    }

    override fun onBackPressed() {
        super.onBackPressed()//按back返回数据
        val intent = Intent()
        intent.putExtra("data_return",  "Hello第一个")
        setResult(RESULT_OK, intent)
        finish()
    }
}