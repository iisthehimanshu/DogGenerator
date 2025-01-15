package com.example.doggenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doggenerator.model.DogRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel(private val repository: DogRepository) : ViewModel() {
    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> get() = _imageUrl

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _cachedImages = MutableStateFlow<List<String>>(emptyList())
    val cachedImages: StateFlow<List<String>> get() = _cachedImages

    init {
        _cachedImages.value = repository.getCachedImages()
    }

    fun fetchNewDogImage() {
        viewModelScope.launch {
            _isLoading.value = true
            val newImageUrl = repository.fetchRandomDogImage()
            if (newImageUrl != null) {
                _imageUrl.value = newImageUrl
                repository.addImageToCache(newImageUrl)
                _cachedImages.value = repository.getCachedImages()
            }
            _isLoading.value = false
        }
    }
}
