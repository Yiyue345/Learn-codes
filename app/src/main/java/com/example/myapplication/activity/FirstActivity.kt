package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.FirstLayoutBinding
import com.example.myapplication.jetpack.JetpackActivity


class FirstActivity : BaseActivity() {

    private lateinit var binding: FirstLayoutBinding

    companion object{
        fun actionStart(context: Context, data1: String, data2: String){
            val intent = Intent(context, SecondActivity::class.java).apply {
                putExtra("param1", "data1")
                putExtra("param2", "data2")
            }
//            intent.putExtra("param1", data1)
//            intent.putExtra("param2", data2)
            context.startActivity(intent)
        }
    }


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {//在Activity第一次被创建的时候调用。在这个方法中完成Activity的初始化操作，比如加载布局、绑定事件等
        super.onCreate(savedInstanceState)

        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("FirstActivity", "Task id is $taskId")
        setSupportActionBar(binding.toolbar)


        binding.button1.setOnClickListener {
//            Toast.makeText(this, "不给你冲", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, TryInternet::class.java)
            startActivity(intent)

//            val intent = Intent("com.example.activitytest.ACTION_START") 隐式Intent
//            intent.addCategory("com.example.activitytest.MY_CATEGORY")

//            val data = "Hello 12345678910" 往下一个Activity传值
//            val intent = Intent(this, SecondActivity::class.java)
//            intent.putExtra("extra_data", data)
//            startActivity(intent)
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivityForResult(intent, 1) //已弃用
//            val intent = Intent(this, SecondActivity::class.java)
//            startActivity(intent)

        }


        binding.button1to2.setOnClickListener{
            val inputText = binding.editText.text.toString()
            Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()
            actionStart(this, "data1", "data2")
        }

        binding.button1to3.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        binding.toRecyclerView.setOnClickListener{
            val intent = Intent(this, RecyclerView::class.java)
            startActivity(intent)
        }

        binding.save.setOnClickListener{
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("name", "好好好")
            editor.putInt("age", 666)
            editor.putBoolean("happy", true)
            editor.putString("123", "456")
            editor.apply()
        }



        binding.hatch.setOnClickListener{
            val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
            val name = prefs.getString("name", "")
            val age = prefs.getInt("age", -1)
            val happy = prefs.getBoolean("happy", false)
            Log.d("FirstActivity", "Name is $name")
            Log.d("FirstActivity", "Age is $age")
            Log.d("FirstActivity", "Happy is $happy")
        }

        binding.toFragment.setOnClickListener{
            val intent = Intent(this, FragmentActivity::class.java)
            startActivity(intent)
        }

        binding.toHttp.setOnClickListener{
            val intent = Intent(this, MoreInternet::class.java)
            startActivity(intent)
        }


    }

    override fun onRestart() {
        super.onRestart()
        Log.d("FirstActivity", "onRestart")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            1 -> if (resultCode == RESULT_OK){
                val returnData = data?.getStringExtra("data_return")
                Log.d("FirstActivity", "returned data is $returnData")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_item -> {
                val intent = Intent(this, Material::class.java)
                startActivity(intent)
            }
            R.id.remove_item -> Toast.makeText(this, "You clicked Remove",
                Toast.LENGTH_SHORT).show()
            R.id.Happy -> {
                Toast.makeText(
                    this, "Are you happy?\nI don't know",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, JetpackActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }





}