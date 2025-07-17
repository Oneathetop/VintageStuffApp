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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tiramisuonlineshop.R
import com.example.tiramisuonlineshop.model.Product
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar
import com.example.tiramisuonlineshop.ui.theme.ThemeManager


val sampleProducts = listOf(
    Product("1", "Pokemon Card Collection 1", R.drawable.pokemon_cards,50,"Cards"),
    Product("2", "Pokemon Card Collection 2", R.drawable.cards_3,45,"Cards"),
    Product("3", "Pokemon Card Collection 3", R.drawable.cards_2,60,"Cards"),
    Product("4", "Pokemon Card Collection 4", R.drawable.cards_5,65,"Cards"),
    Product("5", "Pokemon Card Collection 5", R.drawable.cards_4,65,"Cards"),

    Product("6", "Real Madrid", R.drawable.rm_signed,200,"Signed Jerseys"),
    Product("7", "Real Madrid", R.drawable.rm_signed_1,200,"Signed Jerseys"),
    Product("8", "Manchester United", R.drawable.manutd_signed_1,150,"Signed Jerseys"),
    Product("9", "Manchester United", R.drawable.manutd_signed_2,150,"Signed Jerseys"),
    Product("10", "Juventus", R.drawable.juventus_signed,100,"Signed Jerseys"),


    Product("11", "Real Madrid", R.drawable.rm_vintage,180,"Vintage Jerseys"),
    Product("12", "Real Madrid", R.drawable.rm_vintage_1,180,"Vintage Jerseys"),
    Product("13", "Real Madrid", R.drawable.rm_vintage_2,180,"Vintage Jerseys"),
    Product("14", "Real Madrid", R.drawable.rm_vintage_3,180,"Vintage Jerseys"),
    Product("15", "Manchester United", R.drawable.manutd_vintage,50,"Vintage Jerseys"),

    Product("16", "Retro Watch", R.drawable.smartwatch,80,"Vintage Devices"),
    Product("17", "Vintage Camera", R.drawable.retro_camera,100,"Vintage Devices"),
    Product("18", "Game boy", R.drawable.gameboy,70,"Vintage Devices"),
    Product("19", "Gaming Cartridge", R.drawable.gaming_cartridge,50,"Vintage Devices"),

)

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {

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
                painter = rememberAsyncImagePainter(product.imageResId),
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
    var searchQuery by remember { mutableStateOf("") }
    val filteredProducts = sampleProducts.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text="Home",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
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
            BottomNavigationBar(navController)
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

                //
            }
        }
    }
}


