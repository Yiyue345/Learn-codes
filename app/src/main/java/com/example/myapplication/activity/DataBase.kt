package com.example.myapplication.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.MyDatabaseHelper
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDataBaseBinding


class DataBase : BaseActivity() {

    private lateinit var binding: ActivityDataBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDataBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = MyDatabaseHelper(this, "Books.db", 1)

        binding.addData.setOnClickListener {

            val db = dbHelper.writableDatabase
            val values1 = ContentValues().apply {

                put("name", "Ciallo!")
                put("author", "柚子社")
                put("pages", 721)
                put("price", 1000)
            }
            db.insert("Book", null, values1)

            val values2 = ContentValues().apply {

                put("name", "wow")
                put("author", "nobody")
                put("pages", 0)
                put("price", 123)
            }
            db.insert("Book", null, values2)
//            亦可
//            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
//                arrayOf("Ciallo!", "柚子社", "721", "1000")
//            )
//            db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
//                arrayOf("wow", "nobody", "0", "123")
//            )

        }

        binding.deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ? ", arrayOf("500"))
//            亦可db.execSQL("delete from Book where pages > ?", arrayOf("500"))
        }

        binding.upgradeData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 500)
            db.update("Book", values, "name = ?", arrayOf("Ciallo!"))
//        亦可db.execSQL("update Book set price = ? where name = ?", arrayOf("10.99", "The Da Vinci Code"))
        }

        binding.queryData.setOnClickListener {
            val db = dbHelper.writableDatabase

            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d("DataBase", "book name is $name")
                    Log.d("DataBase", "book author is $author")
                    Log.d("DataBase", "book pages is $pages")
                    Log.d("DataBase", "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()

//            亦可val cursor = db.rawQuery("select * from Book", null)
        }

        binding.replaceData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book", null, null)

                val values = ContentValues().apply {
                    put("name", "Ciallo!Ciallo!Ciallo!")
                    put("author", "yuzu-soft")
                    put("pages", 721)
                    put("price", 10000)
                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }


        binding.createDatabase.setOnClickListener {
            dbHelper.writableDatabase
        }

    }
}