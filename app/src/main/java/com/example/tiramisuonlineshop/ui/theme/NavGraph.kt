package com.example.tiramisuonlineshop.ui.theme


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tiramisuonlineshop.model.ConnectivityHandler
import com.example.tiramisuonlineshop.ui.theme.animation.BrightenAnimationScreen
import com.example.tiramisuonlineshop.ui.theme.screens.CartScreen
import com.example.tiramisuonlineshop.ui.theme.screens.FallbackScreen
import com.example.tiramisuonlineshop.ui.theme.screens.GoogleMapScreen
import com.example.tiramisuonlineshop.ui.theme.screens.HomeScreen
import com.example.tiramisuonlineshop.ui.theme.screens.LoginScreen
import com.example.tiramisuonlineshop.ui.theme.screens.ProductDetailsScreen
import com.example.tiramisuonlineshop.ui.theme.screens.RegisterScreen
import com.example.tiramisuonlineshop.ui.theme.screens.SplashScreen
import com.example.tiramisuonlineshop.ui.theme.screens.UserProfileScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("register") { RegisterScreen(navController) }
        composable("login") { ConnectivityHandler(navController = navController) {
            LoginScreen(navController)
        } }
        composable("loginAnimation")
        {
            BrightenAnimationScreen {
                navController.navigate("home")
                {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("home") {
            ConnectivityHandler(navController = navController) {
                HomeScreen(navController = navController)
            }}
        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailsScreen(productId = productId, navController = navController)
            //ProductDetailsScreen(productId = productId, navController = navController)
        }
        composable("cart") { CartScreen(navController) }
        composable("profile") { UserProfileScreen(navController) }
        composable("splash") { SplashScreen(navController)}
        composable("map") { GoogleMapScreen(navController) }
        composable("fallback") {
            FallbackScreen(navController = navController)
        }

    }
        }




