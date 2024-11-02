package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class TitleLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.title, this)
        val titleBack : Button = findViewById(R.id.titleBack)
        val titleEdit : Button = findViewById(R.id.titleEdit)

        titleBack.setOnClickListener{
            val activity = context as Activity
            activity.finish()
        }

        titleEdit.setOnClickListener{
            Toast.makeText(context, "有啥可编辑的", Toast.LENGTH_SHORT).show()
        }

    }

}