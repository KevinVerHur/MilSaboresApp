package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.milsaboresapp.model.Producto

@Composable
fun DetalleProducto(
    producto: Producto,
    onBack: () -> Unit,
    onCarritoClick: () -> Unit,
    onAgregarCarrito: (Producto, Int) -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 30.dp, start = 15.dp, end = 15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF917970)
                )
            }

            IconButton(onClick = onCarritoClick) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito",
                    tint = Color(0xFF917970)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = producto.nombre,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF5D4037)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = rememberAsyncImagePainter(producto.imagenUrl),
            contentDescription = producto.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(15.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = producto.descripcion,
            textAlign = TextAlign.Justify,
            color = Color(0xFF707070),
            fontSize = 21.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "$${producto.precio}",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                fontSize = 21.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF5D4037)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (cantidad > 1) cantidad-- },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF917970),
                    contentColor = Color.White
                )
            ) { Text("-") }

            Text(
                text = "$cantidad",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = { cantidad++ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF917970),
                    contentColor = Color.White
                )
            ) { Text("+") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = { onAgregarCarrito(producto, cantidad) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF917970),
                    contentColor = Color.White
                )
            ) { Text("Agregar al Carrito") }
        }
    }
}
