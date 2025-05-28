package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.runtime.mutableStateListOf
import com.example.tiramisuonlineshop.ui.Product

object CartManager {
    val cartItems = mutableStateListOf<Product>()

    fun addToCart(product: Product) {
        if (!cartItems.any { it.id == product.id }) {
            cartItems.add(product)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}