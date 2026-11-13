package com.example.phonestoreapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonestoreapp.R
import com.example.phonestoreapp.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    // Lấy danh sách góc chụp của sản phẩm, nếu trống thì dùng ảnh mặc định
    val detailImages = if (product.images.isNotEmpty()) product.images else listOf(product.imageRes, product.imageRes, product.imageRes)

    // Trạng thái lưu ảnh đang được chọn phóng to (Sẽ tự động reset khi chọn sản phẩm khác)
    var currentImageRes by remember(product) { mutableStateOf(product.imageRes) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi Tiết Sản Phẩm", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(onClick = onAddToCartClick) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Giỏ hàng")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // 1. Khung hiển thị ảnh lớn góc cạnh đang chọn
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = if (currentImageRes != 0) currentImageRes else product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Thanh chọn các góc chụp chi tiết (Thumbnail Switcher tương tác)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    detailImages.forEach { thumbRes ->
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (currentImageRes == thumbRes) 2.dp else 1.dp,
                                    color = if (currentImageRes == thumbRes) MaterialTheme.colorScheme.primary else Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { currentImageRes = thumbRes }, // Bấm vào ảnh nào, ảnh lớn đổi sang ảnh đó
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = thumbRes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().padding(4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Tên và Hãng sản xuất
                Text(
                    text = product.brand.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 4. Giá sản phẩm
                Text(
                    text = "${String.format("%,.0f", product.price)} đ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // 5. Thông số kỹ thuật
                Text(text = "Thông số kỹ thuật", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "RAM: ${if (product.ram.isNotEmpty()) product.ram else "8GB"}", color = Color.DarkGray)
                    Text(text = "Bộ nhớ trong: ${if (product.rom.isNotEmpty()) product.rom else "256GB"}", color = Color.DarkGray)
                    Text(text = "Kho: ${if (product.stock > 0) product.stock else "Còn hàng"}", color = Color.DarkGray)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Mô tả sản phẩm
                Text(text = "Mô tả sản phẩm", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (product.description.isNotEmpty()) product.description else "Sản phẩm chính hãng, bảo hành 12 tháng tại hệ thống Phone Store.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 7. Nút Thêm vào giỏ hàng
            Button(
                onClick = onAddToCartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Thêm Vào Giỏ Hàng", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}