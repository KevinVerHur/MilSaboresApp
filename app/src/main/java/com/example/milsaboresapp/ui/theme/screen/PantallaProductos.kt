package com.example.milsaboresapp.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.milsaboresapp.ui.components.CartaProducto
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel

import kotlin.collections.map


@Composable
fun DetalleProducto(
    onProductClick: (Int) -> Unit,
    onPerfilClick: () -> Unit,
    viewModel: ProductoViewModel = viewModel()
) {
    val productos by viewModel.products.collectAsState()
    val categories = listOf("all") + productos.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf("all") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(20.dp ,40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Button(
                onClick = { onPerfilClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF917970),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "NUESTROS PRODUCTOS",
            fontWeight = FontWeight(900),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            MenuCategoria(
                categories = categories,
                selected = selectedCategory,
                onSelect = {
                    selectedCategory = it
                    viewModel.filterByCategory(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(productos) { product ->
                CartaProducto (product) {
                    onProductClick(product.id)
                }
            }
        }
    }


}

@Composable
fun MenuCategoria(
    categories: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            )
        ) {
            Text(if (selected == "all") "Todos" else selected)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFFFFF5E1))
            ) {
            categories.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = if (it == "all") "Todos" else it
                        )
                    },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
