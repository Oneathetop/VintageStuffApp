package com.example.tiramisuonlineshop.ui.theme

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object Firestore {
    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore

    fun saveUserProfile(uid: String, fullName: String, phone: String, address: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val userData = hashMapOf(
            "fullName" to fullName,
            "phone" to phone,
            "address" to address
        )

        db.collection("users").document(uid)
            .set(userData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }
}
