package com.example.tiramisuonlineshop.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Datasource {
    fun loadProducts(context: Context, filename: String = "products.json"): List<Product> {
        val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}



