package com.example.phonestoreapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.phonestoreapp.model.Product

class ProductViewModel : ViewModel() {
    val products = listOf(
        Product(id = "1", name = "iPhone 15 Pro Max", price = 34990000.0, brand = "Apple", description = "Đỉnh cao công nghệ"),
        Product(id = "2", name = "Samsung Galaxy S24 Ultra", price = 31990000.0, brand = "Samsung", description = "Quyền năng AI"),
        Product(id = "3", name = "Xiaomi 14 Ultra", price = 29990000.0, brand = "Xiaomi", description = "Nhiếp ảnh Leica"),
        Product(id = "4", name = "OPPO Find X7 Ultra", price = 22990000.0, brand = "OPPO", description = "Camera Hasselblad")
    )

    private val _cartItems = mutableStateListOf<Product>()
    val cartItems: List<Product> get() = _cartItems

    fun addToCart(product: Product) {
        _cartItems.add(product)
    }

    fun removeFromCart(product: Product) {
        _cartItems.remove(product)
    }

    fun getTotalPrice(): Double {
        return _cartItems.sumOf { it.price }
    }
}