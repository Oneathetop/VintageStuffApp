package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Use LaunchedEffect to wait some time (e.g. 2 seconds) then call onTimeout
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000) // 2 seconds delay
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Replace with your app logo image resource or composable
        // Example using an image resource:
        Image(
            painter = painterResource(id = com.example.tiramisuonlineshop.R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )
    }
}
