package com.example.tiramisuonlineshop.ui.theme

import androidx.compose.runtime.mutableStateOf

object ThemeManager {
    val isDarkMode = mutableStateOf(false)

    fun toggleTheme() {
        isDarkMode.value = !isDarkMode.value
    }
}