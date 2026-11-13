package com.example.phonestoreapp.model

data class CartItem(
    val product: Product = Product(),
    var quantity: Int = 1,
    val selectedColor: String = ""
)