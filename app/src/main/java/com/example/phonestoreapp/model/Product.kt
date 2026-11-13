package com.example.phonestoreapp.model

data class Product(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val brand: String = "",
    val imageUrl: String = "",
    val imageRes: Int = 0,
    val images: List<Int> = emptyList(), // Danh sách các góc chụp chi tiết
    val description: String = "",
    val ram: String = "",
    val rom: String = "",
    val stock: Int = 0
)