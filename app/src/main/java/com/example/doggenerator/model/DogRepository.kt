package com.example.doggenerator.model

import com.example.doggenerator.database.LruCacheManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DogRepository(private val context: Context) {
    private val cacheManager = LruCacheManager(context)

    suspend fun fetchRandomDogImage(): String? {
        if (!isInternetAvailable()) return null

        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://dog.ceo/api/breeds/image/random")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val responseText = connection.inputStream.bufferedReader().readText()
                    val jsonResponse = JSONObject(responseText)
                    jsonResponse.getString("message")
                } else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun addImageToCache(imageUrl: String) {
        cacheManager.addImageToCache(imageUrl)
    }

    fun getCachedImages(): List<String> {
        return cacheManager.getCachedImages()
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
