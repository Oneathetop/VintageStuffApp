package com.example.tiramisuonlineshop.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Datasource {
    fun loadProducts(context: Context, filename: String): List<Product> {
        val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<Product>>() {}.type)
    }
}



