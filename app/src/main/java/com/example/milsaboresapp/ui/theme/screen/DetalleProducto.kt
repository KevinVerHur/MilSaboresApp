package com.example.milsaboresapp.ui.theme.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.milsaboresapp.data.EntidadProducto

@Composable
fun DetalleProducto(
    producto: EntidadProducto,
    onBack: () -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(20.dp, 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = producto.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = rememberAsyncImagePainter(producto.imagenUrl),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
                            topEnd = 15.dp,
                            bottomStart = 15.dp,
                            bottomEnd = 15.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = producto.descripcion,
                textAlign = TextAlign.Justify,
                color = Color(0xFF707070),
                fontSize = 21.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$${producto.precio}",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    fontSize = 21.sp,
                    fontStyle = FontStyle.Italic
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
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                Button(
                    onClick = { cantidad++ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    )
                ) { Text("+") }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    )
                ) { Text("Volver") }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    )
                ) { Text("Agregar al Carrito") }
            }
        }
    }
}
