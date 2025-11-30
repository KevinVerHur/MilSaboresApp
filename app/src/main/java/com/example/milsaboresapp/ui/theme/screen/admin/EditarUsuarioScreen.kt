package com.example.milsaboresapp.ui.theme.screen.admin

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
import com.example.milsaboresapp.model.Usuario
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel

@Composable
fun EditarUsuarioScreen(
    navController: NavController,
    userId: Int,
    viewModel: FormulaarioViewModel
) {
    val usuario by viewModel.usuarioActual.collectAsState()

    LaunchedEffect(userId) {
        viewModel.cargarUsuarioPorId(userId)
    }

    usuario?.let { user ->

        var nombre by remember { mutableStateOf(user.nombre) }
        var correo by remember { mutableStateOf(user.correo) }
        var contrasena by remember { mutableStateOf(user.contrasena) }
        var esAdmin by remember { mutableStateOf(user.esAdministrador) }

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
                "Editar Usuario",
                fontWeight = FontWeight(900),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF5D4037),
                modifier = Modifier
                    .fillMaxWidth()
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
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = esAdmin, onCheckedChange = { esAdmin = it })
                Text("Es Administrador?")
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val usuarioActualizado = Usuario(
                        id = user.id,
                        nombre = nombre,
                        correo = correo,
                        contrasena = contrasena,
                        esAdministrador = esAdmin
                    )
                    viewModel.actualizarUsuario(usuarioActualizado)
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
                    viewModel.eliminarUsuario(user)
                    navController.navigate("admin_usuarios") {
                        popUpTo("admin_usuarios") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                )
            ) {
                Text("Eliminar Usuario")
            }
        }
    }
}
