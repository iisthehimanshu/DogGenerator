import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LruCacheManager(context: Context) {

    companion object {
        private const val CACHE_SIZE = 20
        private const val PREF_NAME = "ImageCachePrefs"
        private const val KEY_CACHE = "ImageCache"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    // In-memory LRU Cache using LinkedHashMap
    private val lruCache = object : LinkedHashMap<String, String>(CACHE_SIZE, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean {
            return size > CACHE_SIZE
        }
    }

    init {
        loadCacheFromPreferences()
    }

    // Add a new image URL to the cache
    fun addImageToCache(imageUrl: String) {
        lruCache[imageUrl] = imageUrl
        saveCacheToPreferences()
    }

    // Get all cached images
    fun getCachedImages(): List<String> {
        return lruCache.values.toList()
    }

    // Clear the cache
    fun clearCache() {
        lruCache.clear()
        saveCacheToPreferences()
    }

    // Clear all the cache (memory and SharedPreferences)
    fun clearAllCache() {
        lruCache.clear() // Clear in-memory cache
        sharedPreferences.edit().remove(KEY_CACHE).apply() // Clear SharedPreferences
    }

    private fun saveCacheToPreferences() {
        val serializedCache = gson.toJson(lruCache)
        sharedPreferences.edit()
            .putString(KEY_CACHE, serializedCache)
            .apply()
    }

    private fun loadCacheFromPreferences() {
        val serializedCache = sharedPreferences.getString(KEY_CACHE, null)
        if (serializedCache != null) {
            val type = object : TypeToken<LinkedHashMap<String, String>>() {}.type
            val savedCache: LinkedHashMap<String, String> = gson.fromJson(serializedCache, type)
            lruCache.putAll(savedCache)
        }
    }
}