package com.example.milsaboresapp.ui.theme.screen
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.milsaboresapp.data.UsuarioDAO
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.milsaboresapp.R

@Composable
fun LoginScreen(navController: NavController, usuarioDAO: UsuarioDAO) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correoError by remember { mutableStateOf(false) }
    var correoFormatoError by remember { mutableStateOf(false) }
    var contrasenaError by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.cumpeo),
            contentDescription = "Logo de la app",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Iniciar Sesión",
            fontWeight = FontWeight(700),
            fontSize = 45.sp,
            color = Color(0xFF5D4037)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                correoError = false
                correoFormatoError = false
                mensajeError = null
            },
            label = { Text(
                text = "Correo electrónico",
                color = Color(0xFF808080)
            ) },
            singleLine = true,
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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                contrasenaError = false
                mensajeError = null
            },
            label = { Text(
                text = "Contraseña",
                color = Color(0xFF808080)
            ) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
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

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            ),
            onClick = {
                correoError = correo.isBlank()
                contrasenaError = contrasena.isBlank()

                val correoRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                correoFormatoError = !correoError && !correo.matches(correoRegex)

                if (!correoError && !correoFormatoError && !contrasenaError) {
                    scope.launch {
                        val usuario = usuarioDAO.obtenerUsuarioPorCorreo(correo)

                        if (usuario != null && usuario.contrasena == contrasena) {

                            val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("correo_usuario", usuario.correo)
                            editor.apply()

                            if (usuario.esAdministrador) {
                                navController.navigate("admin_menu") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                navController.navigate("catalogo") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }

                            mensajeError = null
                        } else {
                            mensajeError = "Credenciales incorrectas"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }


        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.navigate("registro") }) {
            Text("¿No tienes cuenta? Regístrate aquí",
                color = Color(0xFF917970))
        }

        mensajeError?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}