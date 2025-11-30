package com.example.milsaboresapp.ui.theme.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AdminMenuScreen(
    navController: NavController,
    onLoadAdminProfile: () -> Unit,
    usuarioNombre: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
            .padding(top = 30.dp)
            .padding(horizontal = 10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("admin_menu") { inclusive = true }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = Color(0xFF5D4037),
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(
                onClick = {
                    onLoadAdminProfile()
                    navController.navigate("perfil")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color(0xFF5D4037),
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Menú Administrador",
            fontWeight = FontWeight(900),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF5D4037),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Bienvenid@ $usuarioNombre",
            fontSize = 17.sp,
            fontStyle = FontStyle.Italic,
            color = Color(0xFF5D4037),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("catalogo") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            )
        ) {
            Text("Ver Productos", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { navController.navigate("admin_productos") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            )
        ) {
            Text("Gestionar Productos", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { navController.navigate("admin_usuarios") },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF917970),
                contentColor = Color.White
            )
        ) {
            Text("Gestionar Usuarios", fontSize = 18.sp)
        }
    }
}
