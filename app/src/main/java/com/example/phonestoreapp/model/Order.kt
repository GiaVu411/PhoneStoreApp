package com.example.phonestoreapp.model

data class Order(
    val orderId: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val orderDate: Long = System.currentTimeMillis(),
    val status: String = "Đang xử lý" // Đang xử lý, Đang giao, Đã giao
)