package com.example.milsaboresapp.ui.theme.screen

import CarritoViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresapp.ui.theme.components.CarritoItemCard

@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.carrito.collectAsState()
    val total by viewModel.total.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 30.dp, start = 15.dp, end = 15.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF917970)
                    )
                }
            }

            Text(
                "Carrito de Compras",
                fontWeight = FontWeight(900),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF5D4037),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        items(items) { item ->
            CarritoItemCard(
                item = item,
                onIncrease = { viewModel.actualizarCantidad(item, item.cantidad + 1) },
                onDecrease = {
                    if (item.cantidad > 1)
                        viewModel.actualizarCantidad(item, item.cantidad - 1)
                    else
                        viewModel.eliminarItem(item)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFE8C7))
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5D4037)
                    )

                    Text(
                        "$total CLP",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5D4037)
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    ),
                    onClick = { }
                ) {
                    Text("Proceder a pagar")
                }
            }
        }
    }
}
