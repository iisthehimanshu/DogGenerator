package com.example.doggenerator
import RecentDogsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doggenerator.ui.theme.DogGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogGeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomDogApp()
                }
            }
        }
    }
}

@Composable
fun RandomDogApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onFirstButtonClick = { navController.navigate("generateDogs") },
                onSecondButtonClick = { navController.navigate("recentDogs") }
            )
        }
        composable("generateDogs") {
            val context = LocalContext.current
            GenerateDogsScreen(context = context)
        }
        composable("recentDogs") {
            val context = LocalContext.current
            RecentDogsScreen(context = context)
        }
    }
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Greeting Text
        Text(
            text = "Random Dog Generator!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // First Button
        Button(
            onClick = onFirstButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(66, 134, 244)
            )
        ) {
            Text(text = "Generate Dogs!")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Second Button
        Button(
            onClick = onSecondButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(66, 134, 244)
            )
        ) {
            Text(text = "My Recently Generated Dogs!")
        }
    }
}