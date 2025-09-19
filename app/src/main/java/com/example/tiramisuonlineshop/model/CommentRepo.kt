package com.example.tiramisuonlineshop.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request

class CommentRepo {
    object CommentRepository {
        private val client = OkHttpClient()
        private val gson = Gson()

         fun fetchRandomComment(): Comment? {
            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/comments")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null

                val type = object : TypeToken<List<Comment>>() {}.type
                val comments: List<Comment> = gson.fromJson(response.body?.string(), type)

                return comments.random() // pick one random comment
            }
        }
    }
}