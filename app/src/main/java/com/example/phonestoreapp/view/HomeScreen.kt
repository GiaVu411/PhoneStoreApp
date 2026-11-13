package com.example.phonestoreapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonestoreapp.R
import com.example.phonestoreapp.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    val searchQueryState = remember { mutableStateOf("") }

    // Danh sách sản phẩm mẫu đã được mở rộng thêm nhiều dòng máy phong phú
    val sampleProducts = listOf(
        Product(
            id = "1",
            name = "iPhone 15 Plus",
            price = 34990000.0,
            brand = "Apple",
            description = "Đỉnh cao công nghệ, khung titan siêu bền",
            imageRes = R.drawable.iphone,
            images = listOf(R.drawable.iphoneback, R.drawable.iphonefront, R.drawable.iphoneside)
        ),
        Product(
            id = "2",
            name = "Samsung Galaxy A17",
            price = 31990000.0,
            brand = "Samsung",
            description = "Quyền năng AI tích hợp, bút S-Pen tiện lợi",
            imageRes = R.drawable.samsung,
            images = listOf(R.drawable.samsungback, R.drawable.samsungfront, R.drawable.samsungside)
        ),
        Product(
            id = "3",
            name = "Xiaomi 17 Ultra",
            price = 29990000.0,
            brand = "Xiaomi",
            description = "Nhiếp ảnh đỉnh cao hợp tác cùng Leica",
            imageRes = R.drawable.xiaomi,
            images = listOf(R.drawable.xiaomiback, R.drawable.xiaomifront, R.drawable.xiaomiside)
        ),
        Product(
            id = "4",
            name = "OPPO Find X9 Ultra",
            price = 22990000.0,
            brand = "OPPO",
            description = "Camera kép tiềm vọng Hasselblad",
            imageRes = R.drawable.oppo,
            images = listOf(R.drawable.oppofindback, R.drawable.oppofindfront, R.drawable.oppofindside)
        ),
        // Bổ sung thêm các sản phẩm hãng khác / phiên bản khác cho phong phú
        Product(
            id = "5",
            name = "iPhone 13",
            price = 19990000.0,
            brand = "Apple",
            description = "Thiết kế trẻ trung, hiệu năng mạnh mẽ ổn định",
            imageRes = R.drawable.iphone13,
            images = listOf(R.drawable.iphone13back, R.drawable.iphone13front, R.drawable.iphone13side)
        ),
        Product(
            id = "6",
            name = "Samsung Galaxy Tab 11",
            price = 9990000.0,
            brand = "Samsung",
            description = "Tầm trung pin trâu, thiết kế mặt lưng kính cao cấp",
            imageRes = R.drawable.samsunggalaxytabs11,
            images = listOf(R.drawable.samsungtabback, R.drawable.samsungtabfront, R.drawable.samsungtabside)
        ),
        Product(
            id = "7",
            name = "Xiaomi Redmi Note 13",
            price = 5490000.0,
            brand = "Xiaomi",
            description = "Màn hình AMOLED sắc nét, sạc nhanh ấn tượng",
            imageRes = R.drawable.xiaomi
        ),
        Product(
            id = "8",
            name = "Xiaomi 17 Ultra",
            price = 10990000.0,
            brand = "OPPO",
            description = "Chuyên gia chân dung, thiết kế mỏng nhẹ thời trang",
            imageRes = R.drawable.xiaomi17ultra
        )
    )

    // Lọc danh sách sản phẩm dựa theo từ khóa tìm kiếm của người dùng
    val filteredProducts = sampleProducts.filter {
        it.name.contains(searchQueryState.value, ignoreCase = true) ||
                it.brand.contains(searchQueryState.value, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Phone Store",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Giỏ hàng"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Thanh tìm kiếm điện thoại
            OutlinedTextField(
                value = searchQueryState.value,
                onValueChange = { searchQueryState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = { Text("Tìm kiếm điện thoại theo tên hoặc hãng...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sản Phẩm Nổi Bật (${filteredProducts.size})",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Lưới hiển thị danh sách sản phẩm (2 cột, cuộn dọc mượt mà)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredProducts) { product ->
                    Card(
                        onClick = { onProductClick(product) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Khung chứa hình ảnh sản phẩm
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = product.imageRes),
                                    contentDescription = product.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            // Thông tin tên và giá sản phẩm
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = product.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${String.format("%,.0f", product.price)} đ",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            // Nút chuyển hướng xem chi tiết
                            Button(
                                onClick = { onProductClick(product) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                Text(text = "Xem chi tiết", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}