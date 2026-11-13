package com.example.phonestoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.phonestoreapp.model.Product
import com.example.phonestoreapp.view.*
import com.example.phonestoreapp.ui.theme.PhoneStoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhoneStoreAppTheme {
                var currentScreen by remember { mutableStateOf("login") }
                var selectedProduct by remember { mutableStateOf(Product()) }
                val cartList = remember { mutableStateListOf<Product>() }

                // Biến lưu trữ tài khoản thực tế do người dùng đăng ký (Mặc định ban đầu để trống)
                var registeredUser by remember { mutableStateOf("") }
                var registeredPass by remember { mutableStateOf("") }

                when (currentScreen) {
                    "login" -> LoginScreen(
                        registeredUser = registeredUser, // Truyền tài khoản đã đăng ký vào
                        registeredPass = registeredPass,
                        onLoginSuccess = { currentScreen = "home" },
                        onNavigateToRegister = { currentScreen = "register" }
                    )
                    "register" -> RegisterScreen(
                        onRegisterSuccess = { newUsername, newPassword ->
                            // Lưu lại tài khoản và mật khẩu do người dùng vừa nhập lúc đăng ký
                            registeredUser = newUsername
                            registeredPass = newPassword
                            currentScreen = "login" // Đăng ký xong chuyển về màn hình đăng nhập
                        },
                        onBackToLogin = { currentScreen = "login" }
                    )
                    "home" -> HomeScreen(
                        onProductClick = { product ->
                            selectedProduct = product
                            currentScreen = "detail"
                        },
                        onCartClick = {
                            currentScreen = "cart"
                        }
                    )
                    "detail" -> ProductDetailScreen(
                        product = selectedProduct,
                        onBackClick = { currentScreen = "home" },
                        onAddToCartClick = {
                            cartList.add(selectedProduct)
                            currentScreen = "cart"
                        }
                    )
                    "cart" -> CartScreen(
                        cartItems = cartList,
                        totalPrice = cartList.sumOf { it.price },
                        onBackClick = { currentScreen = "home" },
                        onRemoveItem = { productToRemove ->
                            cartList.remove(productToRemove)
                        }
                    )
                }
            }
        }
    }
}