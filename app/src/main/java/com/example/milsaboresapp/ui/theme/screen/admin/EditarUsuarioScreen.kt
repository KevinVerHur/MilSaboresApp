package com.example.milsaboresapp.ui.theme.screen.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                .padding(24.dp)
        ) {
            Text("Editar Usuario", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") })
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(value = contrasena, onValueChange = { contrasena = it }, label = { Text("Contraseña") })
            Spacer(Modifier.height(10.dp))

            Row {
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }

            Spacer(Modifier.height(20.dp))

            Button(
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                onClick = {
                    viewModel.eliminarUsuario(user)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar Usuario")
            }
        }
    }
}
