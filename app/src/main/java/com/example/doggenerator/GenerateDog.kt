package com.example.doggenerator

import LruCacheManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

@Composable
fun GenerateDogsScreen(context: Context) {
    // State to hold the currently displayed image
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Cache Manager instance
    val cacheManager = remember { LruCacheManager(context) }
    val cachedImages = remember { mutableStateOf(cacheManager.getCachedImages()) }

    // Coroutine Scope
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display the fetched image if available
            if (imageUrl != null) {
                val painter = rememberAsyncImagePainter(imageUrl)
                Image(
                    painter = painter,
                    contentDescription = "Random Dog Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )
            }

            // "Generate" Button
            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        val newImageUrl = fetchRandomDogImage(context)
                        if (newImageUrl != null) {
                            imageUrl = newImageUrl
                            cacheManager.addImageToCache(newImageUrl) // Add to cache
                            cachedImages.value = cacheManager.getCachedImages() // Update state
                        }
                        isLoading = false
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(66, 134, 244)
                )
            ) {
                Text(text = if (isLoading) "Loading..." else "Generate")
            }
        }
    }
}

suspend fun fetchRandomDogImage(context: Context): String? {
    // Check if the device is connected to the internet
    if (!isInternetAvailable(context)) {
        // Show a toast message if no internet connection
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show()
        }
        return null
    }

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
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

// Helper function to check internet connection
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}