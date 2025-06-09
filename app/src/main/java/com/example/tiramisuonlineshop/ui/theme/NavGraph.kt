package com.example.tiramisuonlineshop.ui.theme


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiramisuonlineshop.ui.CartScreen
import com.example.tiramisuonlineshop.ui.HomeScreen
import com.example.tiramisuonlineshop.ui.ProductDetailsScreen
import com.example.tiramisuonlineshop.ui.theme.screens.LoginScreen
import com.example.tiramisuonlineshop.ui.theme.screens.RegisterScreen
import com.example.tiramisuonlineshop.ui.theme.screens.SplashScreen
import com.example.tiramisuonlineshop.ui.theme.screens.UserProfileScreen



@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(productId = productId, navController = navController)
        }
        composable("cart") { CartScreen(navController) }
        composable("profile") { UserProfileScreen(navController) }
        composable("splash") { SplashScreen(navController)}
            }
        }




