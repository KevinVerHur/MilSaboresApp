package com.example.milsaboresapp.ui.theme.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.milsaboresapp.model.Usuario
import kotlinx.coroutines.launch

@Composable
fun PerfilUsuario(
    usuario: Usuario,
    onActualizarUsuario: (Usuario) -> Unit,
    onBack: () -> Unit
) {
    var modoEdicion by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var correo by remember { mutableStateOf(usuario.correo) }
    var contrasena by remember { mutableStateOf(usuario.contrasena) }
    var imagenPerfil by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imagenPerfil = it.toString()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF917970)
                    )
                }
                Text(
                    text = "Mi Perfil",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF917970)
                )
                IconButton(
                    onClick = {
                        if (modoEdicion) {
                            nombre = usuario.nombre
                            correo = usuario.correo
                            contrasena = usuario.contrasena
                            telefono = ""
                            direccion = ""
                            descripcion = ""
                            imagenPerfil = ""
                        }
                        modoEdicion = !modoEdicion
                    }
                ) {
                    Icon(
                        imageVector = if (modoEdicion) Icons.Default.Close else Icons.Default.Edit,
                        contentDescription = if (modoEdicion) "Cancelar" else "Editar",
                        tint = Color(0xFF917970)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8DDD4))
                        .border(4.dp, Color(0xFF917970), CircleShape)
                        .clickable(enabled = modoEdicion) {
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (imagenPerfil.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(imagenPerfil),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Sin foto",
                            modifier = Modifier.size(80.dp),
                            tint = Color(0xFF917970)
                        )
                    }

                    if (modoEdicion) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = "Cambiar foto",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CampoPerfil(
                icono = Icons.Default.Person,
                etiqueta = "Nombre",
                valor = nombre,
                onValorChange = { nombre = it },
                habilitado = modoEdicion
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoPerfil(
                icono = Icons.Default.Email,
                etiqueta = "Correo",
                valor = correo,
                onValorChange = { correo = it },
                habilitado = modoEdicion
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Contraseña",
                        tint = Color(0xFF917970),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Contraseña",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF917970)
                    )
                }

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    enabled = modoEdicion,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF917970),
                        unfocusedBorderColor = Color(0xFFD4C4BC),
                        disabledBorderColor = Color(0xFFE8DDD4),
                        disabledTextColor = Color.Black
                    ),
                    visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                            Icon(
                                imageVector = if (mostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (mostrarContrasena) "Ocultar" else "Mostrar",
                                tint = Color(0xFF917970)
                            )
                        }
                    },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CampoPerfil(
                icono = Icons.Default.Phone,
                etiqueta = "Teléfono (opcional)",
                valor = telefono,
                onValorChange = { telefono = it },
                habilitado = modoEdicion
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoPerfil(
                icono = Icons.Default.LocationOn,
                etiqueta = "Dirección (opcional)",
                valor = direccion,
                onValorChange = { direccion = it },
                habilitado = modoEdicion
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Descripción",
                        tint = Color(0xFF917970),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Sobre mí (opcional)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF917970)
                    )
                }

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    enabled = modoEdicion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF917970),
                        unfocusedBorderColor = Color(0xFFD4C4BC),
                        disabledBorderColor = Color(0xFFE8DDD4),
                        disabledTextColor = Color.Black
                    ),
                    placeholder = { Text("Cuéntanos algo sobre ti...") },
                    maxLines = 5
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (modoEdicion) {
                Button(
                    onClick = {
                        val usuarioActualizado = usuario.copy(
                            nombre = nombre,
                            correo = correo,
                            contrasena = contrasena
                        )
                        onActualizarUsuario(usuarioActualizado)
                        modoEdicion = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Perfil actualizado correctamente")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF917970),
                        contentColor = Color.White
                    ),
                    enabled = nombre.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Guardar Cambios",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
private fun CampoPerfil(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    habilitado: Boolean
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = etiqueta,
                tint = Color(0xFF917970),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF917970)
            )
        }

        OutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            enabled = habilitado,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF917970),
                unfocusedBorderColor = Color(0xFFD4C4BC),
                disabledBorderColor = Color(0xFFE8DDD4),
                disabledTextColor = Color.Black
            ),
            singleLine = true
        )
    }
}