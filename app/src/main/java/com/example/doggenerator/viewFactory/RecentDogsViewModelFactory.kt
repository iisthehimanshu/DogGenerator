package com.example.doggenerator.viewFactory

import com.example.doggenerator.database.LruCacheManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doggenerator.viewmodel.RecentDogsViewModel

class RecentDogsViewModelFactory(
    private val cacheManager: LruCacheManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecentDogsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecentDogsViewModel(cacheManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
