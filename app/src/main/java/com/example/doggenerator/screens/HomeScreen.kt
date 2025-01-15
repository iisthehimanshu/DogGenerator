package com.example.doggenerator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.doggenerator.navigator.NavigatorAdapter


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    navigator: NavigatorAdapter
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Random Dog Generator!",
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = onFirstButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(66, 134, 244)
            )
        ) {
            Text(text = "Generate Dogs!")
        }

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = onSecondButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(66, 134, 244)
            )
        ) {
            Text(text = "My Recently Generated Dogs!")
        }
    }
}