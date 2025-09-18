package com.example.tiramisuonlineshop.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tiramisuonlineshop.model.ConnectivityObserver
import com.example.tiramisuonlineshop.model.Datasource
import com.example.tiramisuonlineshop.model.NetworkStatus
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FallbackScreen(navController: NavHostController) {
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    var isConnected by remember { mutableStateOf(false) }
    val sampleProducts = remember { Datasource().loadProducts(context) }

    // 🔄 Auto-redirect when internet comes back
    LaunchedEffect(Unit) {
        connectivityObserver.observe().collectLatest { status ->
            isConnected = status == NetworkStatus.Available
            if (isConnected) {
                navController.navigate("login") {
                    popUpTo("fallback") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "No internet connection.\nPlease check your connection.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
