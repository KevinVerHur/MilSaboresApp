package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel

@Composable
fun EditarProductoScreen(
    navController: NavController,
    productoId: Int,
    viewModel: ProductoViewModel
) {
    val producto by viewModel.productoActual.collectAsState()

    LaunchedEffect(productoId) {
        viewModel.cargarProductoPorId(productoId)
    }

    if (producto == null) return
    val p = producto!!

    var nombre by remember(productoId) { mutableStateOf(p.nombre) }
    var descripcion by remember(productoId) { mutableStateOf(p.descripcion) }
    var categoria by remember(productoId) { mutableStateOf(p.categoria) }
    var precio by remember(productoId) { mutableStateOf(p.precio.toString()) }
    var imagenUrl by remember(productoId) { mutableStateOf(p.imagenUrl) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(20.dp)
    ) {

        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF917970)
            )
        }

        Text(
            "Editar Producto",
            fontWeight = FontWeight.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(14.dp))

        SelectorImagenProducto(
            imagenUrl = imagenUrl,
            onImagenSeleccionada = { imagenUrl = it }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.actualizarProducto(
                    Producto(
                        id = p.id,
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        precio = precio.toIntOrNull() ?: p.precio,
                        imagenUrl = imagenUrl
                    )
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            )
        ) {
            Text("Guardar Cambios")
        }

        Spacer(Modifier.height(6.dp))

        Button(
            onClick = {
                viewModel.eliminarProducto(p)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = Color.White
            )
        ) {
            Text("Eliminar Producto")
        }
    }
}
