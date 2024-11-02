package com.example.myapplication.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.myapplication.lei.User


class JetpackViewModel(countReserved: Int) : ViewModel() {

//    val counter = MutableLiveData<Int>()
//    init {
//        counter.value = countReserved
//    }
//
//    fun plusOne() {
//        val count = counter.value ?: 0
//        counter.value = count + 1
//    }
//
//    fun clear() {
//        counter.value = 0
//    }

    // 下面的写法给套了层壳，防止从外部修改counter的值

    val counter: LiveData<Int> get() = _counter

    private val _counter = MutableLiveData<Int>()

    init {
        _counter.value = countReserved
    }

    fun plusOne() {
        val count = _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }

//    private val userLiveData = MutableLiveData<User>()
//    val userName: LiveData<String> = Transformations.map(userLiveData) { user ->
//        "${user.firstName} ${user.lastName}"
//    }

    private val userIDLiveData = MutableLiveData<String>()

    val user: LiveData<User> = userIDLiveData.switchMap { userID -> // 安卓又改，不能用Transformations了
        Repository.getUser(userID)
    }

    fun getUser(userID: String) {
        userIDLiveData.value = userID
    }


}