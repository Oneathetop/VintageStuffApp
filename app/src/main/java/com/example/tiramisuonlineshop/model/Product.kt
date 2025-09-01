package com.example.tiramisuonlineshop.model

data class Product(
    val id: String,
    val name: String,
    val imageResId: String,
    val price: Int,
    val category: String
) {

    fun getImageResId(context: android.content.Context): Int {
        return context.resources.getIdentifier(imageResId,"drawable",context.packageName)
        //return context.resources.getIdentifier(imageResId, "drawable", context.packageName)
    }
}

