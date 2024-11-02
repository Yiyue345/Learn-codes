package com.example.myapplication.jetpack

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JetpackViewModelFactory(private val countReserved: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return JetpackViewModel(countReserved) as T
    }
}