package com.example.myapplication.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.NewFruitAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMaterialBinding
import com.example.myapplication.lei.Fruit
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class Material : BaseActivity() {

    private lateinit var binding: ActivityMaterialBinding

    val fruits = mutableListOf(Fruit("Apple", R.drawable.apple), Fruit("Banana",
        R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon",
        R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape",
        R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry",
        R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango",
        R.drawable.mango))

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerlayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.aToolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.menu)
        }

        binding.navView.setCheckedItem(R.id.navCall)
        binding.navView.setNavigationItemSelectedListener{
            binding.drawerlayout.closeDrawers()
            true
        }

        binding.fab.setOnClickListener{ view ->
            Snackbar.make(view, "Data无辣", Snackbar.LENGTH_SHORT)
                .setAction("反悔") {
                    Toast.makeText(this, "Ciallo～(∠・ω< )⌒★", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "骗你的啦", Toast.LENGTH_SHORT).show()

                }
                .show()

        }

        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = NewFruitAdapter(this, fruitList)
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setColorSchemeResources(R.color.blue)
        binding.swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 加载菜单
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> binding.drawerlayout.openDrawer(GravityCompat.START)
            R.id.add_item -> Toast.makeText(this, "You clicked add",
                Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You clicked Remove",
                Toast.LENGTH_SHORT).show()
            R.id.Happy -> Toast.makeText(this, "Are you happy?\nI don't know",
                Toast.LENGTH_SHORT).show()

        }
        return true
    }

    private fun  initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: NewFruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }

        }

    }


}