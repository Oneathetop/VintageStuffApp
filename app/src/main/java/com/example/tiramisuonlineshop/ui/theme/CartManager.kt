package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.runtime.mutableStateListOf
import com.example.tiramisuonlineshop.model.Product


object CartManager {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems


    fun addToCart(product: Product, quantity: Int) {
        val existingItem = _cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            _cartItems.add(CartItem(product, quantity))
        }
    }

    fun clearCart() {
        _cartItems.clear()
    }
}

data class CartItem(
    val product: Product,
    var quantity: Int
)