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
import com.example.milsaboresapp.ui.screen.PantallaProductos
import com.example.milsaboresapp.ui.splash.SplashScreen
import com.example.milsaboresapp.ui.theme.MilsaboresappTheme
import com.example.milsaboresapp.ui.theme.screen.LoginScreen
import com.example.milsaboresapp.ui.theme.screen.RegistroScreen
import com.example.milsaboresapp.ui.theme.screen.PerfilUsuario
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel
import com.example.milsaboresapp.ui.theme.viewModel.ProductoViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.navigation.NavHostController
import com.example.milsaboresapp.model.Usuario
import com.example.milsaboresapp.ui.theme.screen.CrearProductoScreen
import com.example.milsaboresapp.ui.theme.screen.EditarProductoScreen
import com.example.milsaboresapp.ui.theme.screen.ListaProductosAdminScreen
import com.example.milsaboresapp.ui.theme.screen.admin.AdminMenuScreen
import com.example.milsaboresapp.ui.theme.screen.admin.EditarUsuarioScreen
import com.example.milsaboresapp.ui.theme.screen.admin.ListaUsuariosScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "usuario.db"
        ).fallbackToDestructiveMigration()
            .build()

        crearAdminSiNoExiste(db)
        setContent {
            MilsaboresappTheme {
                FormularioApp()
            }
        }
    }
    private fun crearAdminSiNoExiste(database: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = database.UsuarioDAO()
            val admin = dao.obtenerUsuarioPorCorreo("admin@milsabores.com")

            if (admin == null) {
                dao.insertarUsuario(
                    Usuario(
                        nombre = "Admin",
                        correo = "admin@milsabores.com",
                        contrasena = "123456",
                        esAdministrador = true
                    )
                )
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

    val formularioViewModel = remember { FormulaarioViewModel(database.UsuarioDAO()) }
    val productoViewModel = remember { ProductoViewModel(RepositorioProductos(database.ProductoDAO())) }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController, database.UsuarioDAO())
        }

        composable("registro") {
            RegistroScreen(formularioViewModel, navController)
        }

        composable("catalogo") {
            CatalogoApp(
                navController = navController,
                productoViewModel = productoViewModel,
                formularioViewModel = formularioViewModel
            )
        }

        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productId") ?: 0
            val productos by productoViewModel.products.collectAsState()
            val producto = productos.find { it.id == id }

            if (producto == null) {
                CircularProgressIndicator()
            } else {
                com.example.milsaboresapp.ui.theme.screen.DetalleProducto(
                    producto = producto,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("perfil") {
            val usuario by formularioViewModel.usuarioActual.collectAsState()

            usuario?.let { user ->
                PerfilUsuario(
                    usuario = user,
                    onActualizarUsuario = { actualizado ->
                        formularioViewModel.actualizarUsuario(actualizado)
                        formularioViewModel.cargarUsuarioPorCorreo(actualizado.correo)
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable("admin_menu") {
            AdminMenuScreen(navController)
        }

        composable("admin_productos") {
            ListaProductosAdminScreen(navController, productoViewModel)
        }

        composable("crear_producto") {
            CrearProductoScreen(navController, productoViewModel)
        }

        composable(
            "editar_producto/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditarProductoScreen(navController, id, productoViewModel)
        }

        composable("admin_usuarios") {
            ListaUsuariosScreen(navController, formularioViewModel)
        }

        composable(
            "editar_usuario/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            EditarUsuarioScreen(navController, id, formularioViewModel)
        }
    }
}




@Composable
fun CatalogoApp(
    navController: NavHostController,
    productoViewModel: ProductoViewModel,
    formularioViewModel: FormulaarioViewModel
) {
    val context = LocalContext.current

    PantallaProductos(
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

