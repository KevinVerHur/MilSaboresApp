package com.example.milsaboresapp.ui.theme.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel

@Composable
fun ListaUsuariosScreen(navController: NavController, viewModel: FormulaarioViewModel) {

    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    val usuarios = viewModel.usuarios.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color(0xFFFFF5E1))
    ) {
        Text("Usuarios Registrados", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(20.dp))

        usuarios.forEach { usuario ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("editar_usuario/${usuario.id}")
                    }
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Nombre: ${usuario.nombre}")
                    Text("Correo: ${usuario.correo}")
                    Text("Rol: ${if (usuario.esAdministrador) "Admin" else "Usuario"}")
                }
            }
        }
    }
}
