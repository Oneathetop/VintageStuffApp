package com.example.tiramisuonlineshop.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
        val id: Int,
        val body: String,
        val user: User
    ) : Parcelable

@Parcelize
data class User(
        val username: String
) : Parcelable

data class CommentResponse(
        val comments: List<Comment>
)