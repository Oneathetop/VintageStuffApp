package com.example.tiramisuonlineshop.ui.theme.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.tiramisuonlineshop.R.drawable.cards_2
import com.example.tiramisuonlineshop.R.drawable.cards_3
import com.example.tiramisuonlineshop.R.drawable.cards_4
import com.example.tiramisuonlineshop.R.drawable.cards_5
import com.example.tiramisuonlineshop.R.drawable.gameboy
import com.example.tiramisuonlineshop.R.drawable.gaming_cartridge
import com.example.tiramisuonlineshop.R.drawable.juventus_signed
import com.example.tiramisuonlineshop.R.drawable.manutd_signed_1
import com.example.tiramisuonlineshop.R.drawable.manutd_signed_2
import com.example.tiramisuonlineshop.R.drawable.manutd_vintage
import com.example.tiramisuonlineshop.R.drawable.pokemon_cards
import com.example.tiramisuonlineshop.R.drawable.retro_camera
import com.example.tiramisuonlineshop.R.drawable.rm_signed
import com.example.tiramisuonlineshop.R.drawable.rm_signed_1
import com.example.tiramisuonlineshop.R.drawable.rm_vintage
import com.example.tiramisuonlineshop.R.drawable.rm_vintage_1
import com.example.tiramisuonlineshop.R.drawable.rm_vintage_2
import com.example.tiramisuonlineshop.R.drawable.rm_vintage_3
import com.example.tiramisuonlineshop.R.drawable.smartwatch
import com.example.tiramisuonlineshop.model.Product
import com.example.tiramisuonlineshop.ui.theme.BottomNavigationBar
import com.example.tiramisuonlineshop.ui.theme.CartManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(productId: String, navController: NavHostController) {

    val sampleProducts = listOf(
        Product(
            "1",
            "Pokemon Card Set Blue Edition",
            pokemon_cards,
            50,
            "Cards"
        ),
        Product(
            "2",
            "Pokemon Card Set Ocean Edition",
            cards_3,
            45,
            "Cards"
        ),
        Product(
            "3",
            "Pokemon Card Set Nature Edition",
            cards_2,
            60,
            "Cards"
        ),
        Product(
            "4",
            "Poker Card Pack All Black Edition",
            cards_5,
            65,
            "Cards"
        ),
        Product(
            "5",
            "Poker Card Pack Black and Silver Edition",
            cards_4,
            65,
            "Cards"
        ),
        Product(
            "6",
            "Real Madrid 2015",
            rm_signed,
            200,
            "Signed Jerseys"
        ),
        Product(
            "7",
            "Real Madrid 2017",
            rm_signed_1,
            200,
            "Signed Jerseys"
        ),
        Product(
            "8",
            "Man United 2000",
            manutd_signed_1,
            150,
            "Signed Jerseys"
        ),
        Product(
            "9",
            "Man United 1999",
            manutd_signed_2,
            150,
            "Signed Jerseys"
        ),
        Product(
            "10",
            "Juventus 2016",
            juventus_signed,
            100,
            "Signed Jerseys"
        ),
        Product(
            "11",
            "Real Madrid 2005 Home",
            rm_vintage,
            180,
            "Vintage Jerseys"
        ),
        Product(
            "12",
            "Real Madrid 2006 Away",
            rm_vintage_1,
            180,
            "Vintage Jerseys"
        ),
        Product(
            "13",
            "Real Madrid 2010 Home",
            rm_vintage_2,
            180,
            "Vintage Jerseys"
        ),
        Product(
            "14",
            "Real Madrid 2011 Away",
            rm_vintage_3,
            180,
            "Vintage Jerseys"
        ),
        Product(
            "15",
            "Man United 1998 Home",
            manutd_vintage,  50,
            "Vintage Jerseys"
        ),
        Product(
            "16",
            "Retro Watch",
            smartwatch,
            80,
            "Vintage Devices"
        ),
        Product(
            "17",
            "Vintage Camera",
            retro_camera,
            100,
            "Vintage Devices"
        ),
        Product(
            "18",
            "Game boy",
            gameboy,
            70,
            "Vintage Devices"
        ),
        Product(
            "19",
            "Gaming Cartridge",
            gaming_cartridge,
            50,
            "Vintage Devices"
        )
    )
    val product = sampleProducts.find { it.id == productId }

    var quantity by remember { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()
    val bounceAnim = remember { Animatable(1f) }
    var convertedPrice by remember { mutableStateOf<String?>(null) }
    var isLoadingConversion by remember { mutableStateOf(true) }
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController,currentRoute)
        }
    ) { padding ->

        if (product != null) {
            LaunchedEffect(product) {
                product.let {
                    isLoadingConversion = true
                    try {
                        val response = withContext(Dispatchers.IO) {
                            URL("https://api.exchangerate-api.com/v4/latest/USD").readText()
                        }
                        val rate = JSONObject(response).getJSONObject("rates").getDouble("LKR")
                        val lkrPrice = rate * it.price
                        convertedPrice = "රු%.2f".format(lkrPrice)
                    } catch (_: Exception) {
                        convertedPrice = "Conversion failed"
                    } finally {
                        isLoadingConversion = false
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.imageResId),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .graphicsLayer(
                            scaleX = bounceAnim.value,
                            scaleY = bounceAnim.value
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "USD Price: $${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                if (isLoadingConversion) {
                    Text(
                        "Converting to LKR...",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                        )
                } else {
                    convertedPrice?.let {
                        Text(
                            "LKR Price: $it",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }


                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Quantity",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }

                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    IconButton(onClick = { quantity++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }

                Button(
                    onClick = {
                        scope.launch {
                            // Trigger bounce animation
                            bounceAnim.animateTo(1.2f, animationSpec = tween(150))
                            bounceAnim.animateTo(1f, animationSpec = tween(150))
                            delay(150)

                            // Add to cart and navigate
                            CartManager.addToCart(product, quantity)
                            navController.navigate("cart")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add to Cart")
                }
            }
        } else {
            Text("Product not found", modifier = Modifier.padding(16.dp))
        }
    }
}
