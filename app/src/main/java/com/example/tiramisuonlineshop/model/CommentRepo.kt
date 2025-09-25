package com.example.tiramisuonlineshop.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class CommentRepo {
    object CommentRepository {
        private val client = OkHttpClient()
        private val gson = Gson()

         fun fetchRandomComment(): Comment? {
            val request = Request.Builder()
                .url("https://dummyjson.com/comments")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null

                val jsonString = response.body?.string()
                val commentResponse = gson.fromJson(jsonString, CommentResponse::class.java)

                return commentResponse.comments.random() // pick one random comment
            }
        }
    }
}