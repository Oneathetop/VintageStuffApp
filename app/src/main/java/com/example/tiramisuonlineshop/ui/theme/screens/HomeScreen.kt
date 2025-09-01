package com.example.tiramisuonlineshop.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tiramisuonlineshop.model.Datasource
import com.example.tiramisuonlineshop.model.Product
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar
import com.example.tiramisuonlineshop.ui.theme.ThemeManager

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column (modifier = Modifier.width(240.dp)){
            Image(
                //painter = painterResource(id = product.imageResId),
                painter = painterResource(product.getImageResId(context)),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current

    val sampleProducts = remember { Datasource().loadProducts(context) }

    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts = sampleProducts.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text="Home",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        //style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        ) },

                actions = {
                    IconButton(onClick = { ThemeManager.toggleTheme() }) {
                        Icon(
                            imageVector = if (ThemeManager.isDarkMode.value)
                                Icons.Default.LightMode
                            else
                                Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController,currentRoute)
        }
    ) { padding ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val isWide = maxWidth > 600.dp
            val columns = if (isWide) 2 else 1

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search products") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }

                //
                val categorized = filteredProducts.groupBy { it.category }

                categorized.forEach { (category, itemsInCategory) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(itemsInCategory) { product ->
                                Box{
                                    ProductCard(product = product) {
                                        navController.navigate("details/${product.id}")
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}


