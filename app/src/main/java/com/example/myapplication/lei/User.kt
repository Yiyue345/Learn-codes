package com.example.myapplication.lei

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var firstname: String, var lastname: String, var age: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}