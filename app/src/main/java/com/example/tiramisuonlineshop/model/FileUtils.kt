package com.example.tiramisuonlineshop.model

import android.content.Context
import com.google.gson.Gson
import java.io.File

object FileUtils {
    private const val FILE_NAME = "suggestion.json"

    fun saveSuggestion(context: Context, suggestion: Suggestion) {
        val file = File(context.filesDir, FILE_NAME)
        val json = Gson().toJson(suggestion)
        file.writeText(json)
    }

    fun readSuggestion(context: Context): Suggestion? {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return null
        val json = file.readText()
        return Gson().fromJson(json, Suggestion::class.java)
    }
}