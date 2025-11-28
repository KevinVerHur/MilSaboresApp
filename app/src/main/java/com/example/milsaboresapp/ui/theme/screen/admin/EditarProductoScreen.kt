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
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Text("Editar Producto", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(20.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("Imagen URL") })
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            Spacer(Modifier.height(20.dp))

            Button(
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                onClick = {
                    viewModel.eliminarProducto(p)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar producto")
            }
        }
    }
}
