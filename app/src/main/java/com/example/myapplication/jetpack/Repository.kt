package com.example.myapplication.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.lei.User

object Repository {

    fun getUser(userID: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        liveData.value = User(userID, userID, 0)
        return liveData
    }
}