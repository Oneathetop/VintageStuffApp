package com.example.tiramisuonlineshop.model

import android.content.Context
import com.google.gson.Gson

class OfflineDataSource {
    object JsonUtils {
        fun loadAboutUs(context: Context): DataModel {
            val jsonString = context.assets.open("about_us.json")
                .bufferedReader()
                .use { it.readText() }

            return Gson().fromJson(jsonString, DataModel::class.java)
        }
    }
}