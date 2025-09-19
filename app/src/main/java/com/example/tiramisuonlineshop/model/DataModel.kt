package com.example.tiramisuonlineshop.model

    data class Contact(
        val email: String,
        val phone: String,
        val address: String
    )

    data class DataModel(
        val aboutUs: AboutUs
    )

    data class AboutUs(
        val title: String,
        val description: String,
        val mission: String,
        val values: List<String>,
        val contact: Contact
    )

