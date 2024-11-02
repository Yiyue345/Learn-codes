package com.example.myapplication.activity

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.FruitAdapter
import com.example.myapplication.R
import com.example.myapplication.lei.Fruit


class ListView : AppCompatActivity() {

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(1) // 或者在这里关掉通知，1是id
        manager.cancel(2) // 2也是id
        initFruits()
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)
        val listView : ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener{ _, _, position, _ ->
            val fruit = fruitList[position]
            Toast.makeText(this, fruit.name, Toast.LENGTH_SHORT).show()
        }

    }

    private fun initFruits(){
        repeat(3){
            fruitList.add(Fruit("Apple", R.drawable.apple))
            fruitList.add(Fruit("Banana", R.drawable.banana))
            fruitList.add(Fruit("Orange", R.drawable.orange))
            fruitList.add(Fruit("Watermelon", R.drawable.watermelon))
            fruitList.add(Fruit("Pear", R.drawable.pear))
            fruitList.add(Fruit("Grape", R.drawable.grape))
            fruitList.add(Fruit("Pineapple", R.drawable.pineapple))
            fruitList.add(Fruit("Strawberry", R.drawable.strawberry))
            fruitList.add(Fruit("Cherry", R.drawable.cherry))
            fruitList.add(Fruit("Mango", R.drawable.mango))
        }
    }

}