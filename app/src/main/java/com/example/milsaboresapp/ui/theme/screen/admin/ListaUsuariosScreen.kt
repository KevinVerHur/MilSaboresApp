package com.example.milsaboresapp.ui.theme.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel
@Composable
fun ListaUsuariosScreen(
    navController: NavController,
    viewModel: FormulaarioViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    val usuarios = viewModel.usuarios.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 30.dp, start = 15.dp, end = 15.dp)
    ) {

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

            IconButton(onClick = { navController.navigate("crear_usuario") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar usuario",
                    tint = Color(0xFF917970)
                )
            }
        }

        Text(
            "Usuarios Registrados",
            fontWeight = FontWeight(900),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        usuarios.forEach { usuario ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("editar_usuario/${usuario.id}")
                    },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF5E1)
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = usuario.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF5D4037)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Correo: ${usuario.correo}",
                        color = Color(0xFF707070),
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Rol: ${if (usuario.esAdministrador) "Admin" else "Usuario"}",
                        fontWeight = FontWeight.SemiBold,
                        color = if (usuario.esAdministrador) Color(0xFF673AB7) else Color(0xFF5D4037),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

}


