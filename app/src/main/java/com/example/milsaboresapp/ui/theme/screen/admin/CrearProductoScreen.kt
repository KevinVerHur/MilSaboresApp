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
fun CrearProductoScreen(navController: NavController, viewModel: ProductoViewModel) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(30.dp)
    ) {

        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF917970)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Crear Producto",
            fontWeight = FontWeight(900),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = false
            },
            label = { Text("Nombre", color = Color(0xFF808080)) },
            isError = nombreError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción", color = Color(0xFF808080)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            label = { Text("Categoría", color = Color(0xFF808080)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = {
                precio = it
                precioError = false
            },
            label = { Text("Precio (ej: 3500)", color = Color(0xFF808080)) },
            isError = precioError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text("Ruta imagen drawable", color = Color(0xFF808080)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                nombreError = nombre.isBlank()
                precioError = precio.isBlank() || precio.toIntOrNull() == null

                if (!nombreError && !precioError) {
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
            }
        ) {
            Text("Guardar Producto")
        }
    }
}
