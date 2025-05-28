package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("cart") },
            label = { Text("Cart") },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("login") },
            label = { Text("Logout") },
            icon = {Icon(Icons.Default.Logout, contentDescription = "Logout")}
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
        )

    }
}
