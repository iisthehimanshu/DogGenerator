import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RecentDogsScreen(context: Context) {
    // Create an instance of LruCacheManager
    val cacheManager = remember { LruCacheManager(context) }

    // Get cached image URLs from LRU cache (using state to trigger recomposition)
    var cachedImages by remember { mutableStateOf(cacheManager.getCachedImages()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Center everything inside the Box
    ) {
        if (cachedImages.isEmpty()) {
            // Show a message if there are no images
            Text(
                text = "No images available!",
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(), // Fill width to center the contents horizontally
                verticalArrangement = Arrangement.Center, // Center vertically
                horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
            ) {
                // Display horizontally scrolling gallery of images
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(cachedImages) { imageUrl ->
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                        ) {
                            // Load and display the image using AsyncImage
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Recent Dog Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Clear Cache Button below the gallery
                Button(
                    onClick = {
                        // Clear the cache and update the UI
                        cacheManager.clearAllCache()
                        cachedImages = emptyList() // Immediately update the list to be empty
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(66, 134, 244)
                    )
                ) {
                    Text(text = "Clear All Cache")
                }
            }
        }
    }
}