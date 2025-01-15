package com.example.doggenerator.viewmodel

import com.example.doggenerator.database.LruCacheManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecentDogsViewModel(private val cacheManager: LruCacheManager) : ViewModel() {


    private val _cachedImages = MutableStateFlow<List<String>>(emptyList())
    val cachedImages: StateFlow<List<String>> = _cachedImages

    init {

        loadCachedImages()
    }


    private fun loadCachedImages() {
        _cachedImages.value = cacheManager.getCachedImages()
    }


    fun clearCache() {
        viewModelScope.launch(Dispatchers.IO) {
            cacheManager.clearAllCache()
            _cachedImages.value = emptyList()
        }
    }
}
