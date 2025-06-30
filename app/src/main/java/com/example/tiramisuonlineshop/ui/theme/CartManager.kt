package com.example.tiramisuonlineshop.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.tiramisuonlineshop.model.Product
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


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

    fun saveCartToFirestore() {
        val userId = Firebase.auth.currentUser?.uid ?: return
        val db = Firebase.firestore

        val cartData = _cartItems.map {
            hashMapOf(
                "productId" to it.product.id,
                "productName" to it.product.name,
                "productImageUrl" to it.product.imageResId,
                "productPrice" to it.product.price,
                "quantity" to it.quantity
            )
        }

        db.collection("users").document(userId)
            .collection("cart")
            .document("items")
            .set(mapOf("items" to cartData))
            .addOnSuccessListener {
                Log.d("Cart", "Cart saved to Firestore")
            }
            .addOnFailureListener { e ->
                Log.e("Cart", "Failed to save cart: ${e.message}")
            }
    }

}

data class CartItem(
    val product: Product,
    var quantity: Int
)