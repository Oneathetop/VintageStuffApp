package com.example.tiramisuonlineshop.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiramisuonlineshop.ui.CartScreen
import com.example.tiramisuonlineshop.ui.theme.LoginScreen
import com.example.tiramisuonlineshop.ui.theme.RegisterScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.res.painterResource
import com.example.tiramisuonlineshop.ui.theme.SplashScreen
import com.example.tiramisuonlineshop.ui.theme.UserProfileScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(productId = productId, navController = navController)
        }
        composable("cart") { CartScreen(navController) }
        composable("profile") { UserProfileScreen(navController) }
        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }
}


