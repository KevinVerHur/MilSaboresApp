package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel

@Composable
fun ListaProductosAdminScreen(navController: NavController, viewModel: ProductoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
    }

    val productos = viewModel.products.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Gestión de Productos", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("crear_producto") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Producto")
        }

        Spacer(Modifier.height(20.dp))

        productos.forEach { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("editar_producto/${producto.id}")
                    }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Nombre: ${producto.nombre}")
                    Text("Categoría: ${producto.categoria}")
                    Text("Precio: ${producto.precio} CLP")
                }
            }
        }
    }
}
