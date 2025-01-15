package com.example.doggenerator.database

import android.content.Context
import com.google.gson.reflect.TypeToken

class LruCacheManager(context: Context) {

    companion object {
        private const val CACHE_SIZE = 20
        private const val PREF_NAME = "ImageCachePrefs"
        private const val KEY_CACHE = "ImageCache"
    }

    private val sharedPreferencesHelper = SharedPreferencesHelper(context, PREF_NAME)


    private val lruCache = object : LinkedHashMap<String, String>(CACHE_SIZE, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean {
            return size > CACHE_SIZE
        }
    }

    init {
        loadCacheFromPreferences()
    }


    fun addImageToCache(imageUrl: String) {
        lruCache[imageUrl] = imageUrl
        saveCacheToPreferences()
    }


    fun getCachedImages(): List<String> {
        return lruCache.values.toList()
    }


    fun clearCache() {
        lruCache.clear()
        saveCacheToPreferences()
    }


    fun clearAllCache() {
        lruCache.clear()
        sharedPreferencesHelper.remove(KEY_CACHE)
    }

    private fun saveCacheToPreferences() {
        sharedPreferencesHelper.saveObject(KEY_CACHE, lruCache)
    }

    private fun loadCacheFromPreferences() {
        val type = object : TypeToken<LinkedHashMap<String, String>>() {}.type
        val savedCache: LinkedHashMap<String, String>? =
            sharedPreferencesHelper.loadObject(KEY_CACHE, type)
        savedCache?.let { lruCache.putAll(it) }
    }
}
