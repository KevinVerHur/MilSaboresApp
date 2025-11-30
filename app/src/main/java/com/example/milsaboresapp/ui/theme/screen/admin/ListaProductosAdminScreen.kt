package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel

@Composable
fun ListaProductosAdminScreen(navController: NavController, viewModel: ProductoViewModel) {

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
    }

    val productos = viewModel.products.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 30.dp, start = 15.dp, end = 15.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF917970)
                    )
                }

                IconButton(onClick = { navController.navigate("crear_producto") }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar producto",
                        tint = Color(0xFF917970)
                    )
                }
            }

            Text(
                "Gestión de Productos",
                fontWeight = FontWeight(900),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF5D4037),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
        }

        items(productos) { producto ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { navController.navigate("editar_producto/${producto.id}") },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5E1))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(model = producto.imagenUrl),
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = producto.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF5D4037)
                        )

                        Spacer(Modifier.height(3.dp))

                        Text(
                            text = producto.descripcion,
                            textAlign = TextAlign.Justify,
                            color = Color(0xFF707070),
                            fontSize = 14.sp,
                            maxLines = 3
                        )

                        Spacer(Modifier.height(3.dp))

                        Text(
                            text = "$${producto.precio}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF5D4037)
                        )
                    }
                }
            }
        }
    }
}
