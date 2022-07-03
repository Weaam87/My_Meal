package com.example.mymeal.model

import java.text.NumberFormat

data class MenuItem(
    val name: String,
    val description: String,
    val price: Double
) {

    fun getFormattedPrice(): String = NumberFormat.getCurrencyInstance().format(price)
}