package com.example.tiramisuonlineshop.model

data class ExchangeRateResponse (
    val rates: Map<String, Double>,
    val base: String
)