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
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel

@Composable
fun CrearUsuarioScreen(navController: NavController, viewModel: FormulaarioViewModel) {

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var esAdmin by remember { mutableStateOf(false) }

    var nombreError by remember { mutableStateOf(false) }
    var correoError by remember { mutableStateOf(false) }
    var correoFormatoError by remember { mutableStateOf(false) }
    var contrasenaError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(20.dp)
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
            text = "Crear Usuario",
            fontWeight = FontWeight(900),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier
                .fillMaxWidth()
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
            value = correo,
            onValueChange = {
                correo = it
                correoError = false
                correoFormatoError = false
            },
            label = { Text("Correo", color = Color(0xFF808080)) },
            isError = correoError || correoFormatoError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                contrasenaError = false
            },
            label = { Text("Contraseña", color = Color(0xFF808080)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = esAdmin,
                onCheckedChange = { esAdmin = it })
            Text(text = "Administrador")
        }

        Spacer(Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                nombreError = nombre.isBlank()
                correoError = correo.isBlank()
                contrasenaError = contrasena.isBlank()

                val correoRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                correoFormatoError = !correoError && !correo.matches(correoRegex)

                if (!nombreError && !correoError && !correoFormatoError && !contrasenaError) {
                    viewModel.agregarUsuario(nombre, correo, contrasena, esAdmin)
                    navController.popBackStack()
                }
            }
        ) {
            Text("Guardar usuario")
        }
    }
}
