package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel

@Composable
fun CrearProductoScreen(navController: NavController, viewModel: ProductoViewModel) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("Nuevo Producto", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio (ej: 3500)") })
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text("Ruta imagen drawable") }
        )
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && precio.isNotBlank()) {
                    val nuevo = Producto(
                        nombre = nombre,
                        descripcion = descripcion,
                        categoria = categoria,
                        precio = precio.toInt(),
                        imagenUrl = imagenUrl
                    )
                    viewModel.insertarProducto(nuevo)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
