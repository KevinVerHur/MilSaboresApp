package com.example.milsaboresapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.milsaboresapp.data.AppDatabase
import com.example.milsaboresapp.data.RepositorioProductos
import com.example.milsaboresapp.ui.screens.DetalleProducto
import com.example.milsaboresapp.ui.splash.SplashScreen
import com.example.milsaboresapp.ui.theme.MilsaboresappTheme
import com.example.milsaboresapp.ui.theme.screen.LoginScreen
import com.example.milsaboresapp.ui.theme.screen.RegistroScreen
import com.example.milsaboresapp.ui.theme.screen.PerfilUsuario
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel
import androidx.compose.material3.CircularProgressIndicator
import com.example.milsaboresapp.model.Usuario
import com.example.milsaboresapp.ui.theme.screen.AdminMenuScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MilsaboresappTheme {
                FormularioApp()
            }
        }
    }
}

@Composable
fun FormularioApp() {
    val context = LocalContext.current

    val database = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "usuario.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    LaunchedEffect(true) {
        val admin = database.UsuarioDAO().obtenerUsuarioPorCorreo("admin@milsabores.com")
        if (admin == null) {
            database.UsuarioDAO().insertarUsuario(
                Usuario(
                    nombre = "Admin",
                    correo = "admin@milsabores.com",
                    contrasena = "123456",
                    esAdministrador = true
                )
            )
        }
    }

    val formularioViewModel = remember { FormulaarioViewModel(database.UsuarioDAO()) }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController, database.UsuarioDAO()) }
        composable("registro") { RegistroScreen(formularioViewModel, navController) }
        composable("catalogo") { CatalogoApp() }
        composable("admin_menu") { AdminMenuScreen(onBack = { navController.popBackStack() })
        }
    }
}


@Composable
fun CatalogoApp() {
    val context = LocalContext.current

    val database = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "usuario.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    val repositorio = remember { RepositorioProductos(database.ProductoDAO()) }
    val productoViewModel = remember { ProductoViewModel(repositorio) }

    val formularioViewModel = remember { FormulaarioViewModel(database.UsuarioDAO()) }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {
        composable("products") {
            DetalleProducto(
                onProductClick = { id ->
                    navController.navigate("productDetail/$id")
                },
                onPerfilClick = {
                    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    val correoGuardado = sharedPreferences.getString("correo_usuario", null)

                    if (correoGuardado != null) {
                        formularioViewModel.cargarUsuarioPorCorreo(correoGuardado)
                        navController.navigate("perfil")
                    }
                },
                viewModel = productoViewModel
            )
        }

        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            val productos by productoViewModel.products.collectAsState()
            val product = productos.find { it.id == productId }

            if (product == null) {
                CircularProgressIndicator()
            } else {
                com.example.milsaboresapp.ui.theme.screen.DetalleProducto(
                    producto = product,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("perfil") {
            val usuario by formularioViewModel.usuarioActual.collectAsState()

            usuario?.let {
                PerfilUsuario(
                    usuario = it,
                    onActualizarUsuario = { },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
