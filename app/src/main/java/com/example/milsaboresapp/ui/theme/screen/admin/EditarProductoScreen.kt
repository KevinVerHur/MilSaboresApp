package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

    producto?.let { p ->

        var nombre by remember { mutableStateOf(p.nombre) }
        var descripcion by remember { mutableStateOf(p.descripcion) }
        var categoria by remember { mutableStateOf(p.categoria) }
        var precio by remember { mutableStateOf(p.precio.toString()) }
        var imagenUrl by remember { mutableStateOf(p.imagenUrl) }

        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF5E1))
                .padding(top = 30.dp, start = 15.dp, end = 15.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF917970)
                    )
                }
            }

            Text(
                "Editar Producto",
                fontWeight = FontWeight(900),
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

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("Imagen URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val actualizado = Producto(
                        id = p.id,
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        precio = precio.toInt(),
                        imagenUrl = imagenUrl
                    )
                    viewModel.actualizarProducto(actualizado)
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

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    viewModel.eliminarProducto(p)
                    navController.navigate("admin_catalogo") {
                        popUpTo("admin_catalogo") { inclusive = true }
                    }
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
}
