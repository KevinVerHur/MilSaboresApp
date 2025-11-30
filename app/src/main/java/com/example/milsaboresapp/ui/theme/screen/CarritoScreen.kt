package com.example.milsaboresapp.ui.theme.screen

import CarritoViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.milsaboresapp.ui.theme.components.CarritoItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel,
    onBack: () -> Unit
) {
    val items by viewModel.carrito.collectAsState()
    val total by viewModel.total.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(20.dp, 30.dp)
    ) {

        TopAppBar(
            title = {
                Text(
                    "Carrito de Compras",
                    color = Color(0xFF5D4037),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    )
                ) {
                    Text("< Volver")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFFF5E1)
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        ) {
            items(items) { item ->
                CarritoItemCard(
                    item = item,
                    onIncrease = {
                        viewModel.actualizarCantidad(item, item.cantidad + 1)
                    },
                    onDecrease = {
                        if (item.cantidad > 1) {
                            viewModel.actualizarCantidad(item, item.cantidad - 1)
                        } else {
                            viewModel.eliminarItem(item)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

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
                onClick = {  }
            ) {
                Text("Proceder a pagar")
            }
        }
    }
}
