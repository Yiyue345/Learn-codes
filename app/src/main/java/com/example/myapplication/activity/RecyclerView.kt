package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.AdvancedFruitAdapter
import com.example.myapplication.R
import com.example.myapplication.lei.Fruit


class RecyclerView : BaseActivity() {

    private val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initFruits()
        val layoutManager = StaggeredGridLayoutManager(3,
            StaggeredGridLayoutManager.VERTICAL)
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = AdvancedFruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }

    private fun initFruits(){
        repeat(3){
            fruitList.add(Fruit(getRandomLengthString("Apple"), R.drawable.apple))
            fruitList.add(Fruit(getRandomLengthString("Banana"), R.drawable.banana))
            fruitList.add(Fruit(getRandomLengthString("Orange"), R.drawable.orange))
            fruitList.add(Fruit(getRandomLengthString("Watermelon"), R.drawable.watermelon))
            fruitList.add(Fruit(getRandomLengthString("Pear"), R.drawable.pear))
            fruitList.add(Fruit(getRandomLengthString("Grape"), R.drawable.grape))
            fruitList.add(Fruit(getRandomLengthString("Pineapple"), R.drawable.pineapple))
            fruitList.add(Fruit(getRandomLengthString("Strawberry"), R.drawable.strawberry))
            fruitList.add(Fruit(getRandomLengthString("Cherry"), R.drawable.cherry))
            fruitList.add(Fruit(getRandomLengthString("Mango"), R.drawable.mango))
        }
    }

    private fun getRandomLengthString(str: String): String{
        val n = (1..20).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(str)
        }
        return builder.toString()
    }

}