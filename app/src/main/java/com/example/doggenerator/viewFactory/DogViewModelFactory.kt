package com.example.doggenerator.viewFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doggenerator.model.DogRepository
import com.example.doggenerator.viewmodel.DogViewModel

class DogViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogViewModel::class.java)) {
            return DogViewModel(DogRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
