package com.example.myapplication

interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}