package com.example.tiramisuonlineshop.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar
import com.example.tiramisuonlineshop.ui.theme.CartManager

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Cart", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)) })
        },
        bottomBar = {
            BottomNavigationBar(navController, currentRoute)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (CartManager.cartItems.isEmpty()) {
                Text("Your cart is empty!", style = MaterialTheme.typography.headlineSmall)
            } else {

                //
                BoxWithConstraints(
                    modifier = Modifier.weight(1f)
                ) {
                    val isWide = maxWidth > 600.dp
                    val columns = if (isWide) 2 else 1
                    val totalPrice = CartManager.cartItems.sumOf { it.product.price * it.quantity }

                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),  
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 8.dp)
                        ) {
                            items(CartManager.cartItems) { item ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = "${item.product.name} x${item.quantity}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Price: $${item.product.price}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Total: $${item.product.price * item.quantity}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))  
                        Text(
                            text = "Total: $${"%.2f".format(totalPrice.toDouble())}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                //
                }

                if (CartManager.cartUsed.value) {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { CartManager.clearCart() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Cart")
                }
            }
        }
    }
}

