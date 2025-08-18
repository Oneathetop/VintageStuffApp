package com.example.tiramisuonlineshop.utils

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("DiscouragedApi")
fun Context.getDrawableId(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}