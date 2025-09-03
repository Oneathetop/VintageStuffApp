package com.example.tiramisuonlineshop.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ConnectivityHandler(navController: NavHostController, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    val scope = rememberCoroutineScope()

    var isConnected by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        connectivityObserver.observe().collectLatest { status ->
            isConnected = status == NetworkStatus.Available
            if (!isConnected) {
                navController.navigate("fallback") {
                    popUpTo("home") { inclusive = true } // optional: remove home from backstack
                }
            }
        }
    }

    if (isConnected) {
        content()
    }
}