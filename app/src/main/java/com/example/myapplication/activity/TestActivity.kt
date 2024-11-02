package com.example.myapplication.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class TestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.test_layout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val buttonTest : Button = findViewById(R.id.buttonTest)
        val buttonBack : Button = findViewById(R.id.buttonBack)
        val helloButton : Button = findViewById(R.id.helloButton)

        helloButton.setOnClickListener{
            Toast.makeText(this, "HELLO没有全部大写！", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this).apply {
                setTitle("你好！")
                setMessage("我也好")
                setCancelable(false) //是否可用back关闭
                setPositiveButton("好"){
                    dialog, which ->
                }
                setNegativeButton("不好"){
                    dialog, which ->
                }
                show()
            }
        }

        buttonTest.setOnClickListener{
            ActivityCollector.finishAll()
        }
        buttonBack.setOnClickListener{
            Toast.makeText(this, "回到起点！", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}