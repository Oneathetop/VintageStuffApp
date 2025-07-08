package com.example.tiramisuonlineshop.ui.theme.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val file = File(context.cacheDir, "profile_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
