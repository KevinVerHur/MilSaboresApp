package com.example.milsaboresapp.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.milsaboresapp.R



@Composable
fun RegistroScreen(viewModel: FormulaarioViewModel, navController: NavController) {
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val usuario by viewModel.usuarios.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    // Estados de error
    var nombreError by remember { mutableStateOf(false) }
    var correoError by remember { mutableStateOf(false) }
    var correoFormatoError by remember { mutableStateOf(false) }
    var contrasenaError by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.cumpeo),
            contentDescription = "Logo de la app",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Registrate",
            fontWeight = FontWeight(700),
            fontSize = 45.sp,
            color = Color(0xFF5D4037)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = false
            },
            label = { Text(
                text = "Ingrese nombre",
                color = Color(0xFF808080)
            ) },
            isError = nombreError,
            modifier = Modifier.fillMaxWidth()
        )
        if (nombreError) {
            Text(
                text = "El nombre no puede estar vacío",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                correoError = false
                correoFormatoError = false
            },
            label = { Text(
                text = "Ingrese correo",
                color = Color(0xFF808080)
            ) },
            isError = correoError || correoFormatoError,
            modifier = Modifier.fillMaxWidth()
        )
        when {
            correoError -> Text(
                text = "El correo no puede estar vacío",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            correoFormatoError -> Text(
                text = "Formato de correo no válido",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                contrasenaError = false
            },
            label = { Text(
                text = "Ingrese contraseña",
                color = Color(0xFF808080)
            ) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Mostrar/Ocultar contraseña")
                }
            },
            isError = contrasenaError,
            modifier = Modifier.fillMaxWidth()
        )
        if (contrasenaError) {
            Text(
                text = "La contraseña no puede estar vacía",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            ),
            onClick = {
                nombreError = nombre.isBlank()
                correoError = correo.isBlank()
                contrasenaError = contrasena.isBlank()

                val correoRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                correoFormatoError = !correoError && !correo.matches(correoRegex)

                if (!nombreError && !correoError && !correoFormatoError && !contrasenaError) {
                    viewModel.agregarUsuario(nombre, correo, contrasena)
                    nombre = ""
                    correo = ""
                    contrasena = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Agregar usuario")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("¿Ya tienes cuenta?",
                color = Color(0xFF917970))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Usuarios guardados", style = MaterialTheme.typography.titleMedium)
        usuario.forEach { u ->
            Text(text = u.nombre)
        }
    }
}