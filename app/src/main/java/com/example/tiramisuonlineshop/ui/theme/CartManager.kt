package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.tiramisuonlineshop.model.Product


object CartManager {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems
    var cartUsed = mutableStateOf(false)

    fun addToCart(product: Product, quantity: Int) {
        val existingItem = _cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            _cartItems.add(CartItem(product, quantity))
        }
        cartUsed.value = true
    }

    fun clearCart() {
        _cartItems.clear()
        cartUsed.value = false
    }
}

data class CartItem(
    val product: Product,
    var quantity: Int
)