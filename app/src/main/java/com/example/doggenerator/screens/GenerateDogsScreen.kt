package com.example.doggenerator.screens


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.doggenerator.NavigatorAdapter
import com.example.doggenerator.viewFactory.DogViewModelFactory
import com.example.doggenerator.viewmodel.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateDogsScreen(
    context: Context,
    viewModel: DogViewModel = viewModel(factory = DogViewModelFactory(context)),
    navigator: NavigatorAdapter
) {
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Generate Dogs!",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(66, 134, 244),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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

                Button(
                    onClick = { viewModel.fetchNewDogImage() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(66, 134, 244)
                    )
                ) {
                    Text(
                        text = "Generate!",
                        color = if (isLoading) Color.Gray else Color.White
                    )
                }
            }
        }
    }
}
