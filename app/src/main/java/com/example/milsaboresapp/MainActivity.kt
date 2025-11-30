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
import com.example.milsaboresapp.ui.theme.screen.CrearProductoScreen
import com.example.milsaboresapp.ui.theme.screen.EditarProductoScreen
import com.example.milsaboresapp.ui.theme.screen.ListaProductosAdminScreen
import com.example.milsaboresapp.ui.theme.screen.admin.AdminMenuScreen
import com.example.milsaboresapp.ui.theme.screen.admin.EditarUsuarioScreen
import com.example.milsaboresapp.ui.theme.screen.admin.ListaUsuariosScreen
import com.example.milsaboresapp.model.Usuario
import androidx.compose.material3.CircularProgressIndicator
import androidx.core.content.edit
import androidx.navigation.NavHostController

import com.example.milsaboresapp.data.repositories.MealRepository
import com.example.milsaboresapp.di.RetrofitInstance
import com.example.milsaboresapp.ui.screen.PremiumProductsScreen
import com.example.milsaboresapp.ui.theme.viewModel.PremiumProductsViewModel
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
        ).fallbackToDestructiveMigration().build()

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
                        nombre = "Admin Mil Sabores",
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
        ).fallbackToDestructiveMigration().build()
    }

    val formularioViewModel = remember { FormulaarioViewModel(database.UsuarioDAO()) }
    val productoViewModel = remember {
        ProductoViewModel(RepositorioProductos(database.ProductoDAO()))
    }

    val mealRepository = remember { MealRepository(RetrofitInstance.api) }
    val premiumProductsViewModel = remember { PremiumProductsViewModel(mealRepository) }

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
                formularioViewModel = formularioViewModel,
                premiumProductsViewModel = premiumProductsViewModel
            )
        }

        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productId") ?: 0
            val productos by productoViewModel.products.collectAsState()
            val producto = productos.find { it.id == id }

            if (producto == null) CircularProgressIndicator()
            else com.example.milsaboresapp.ui.theme.screen.DetalleProducto(
                producto = producto,
                onBack = { navController.popBackStack() }
            )
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
            val prefs = LocalContext.current.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val correo = prefs.getString("correo_usuario", null)

            LaunchedEffect(correo) {
                if (correo != null) {
                    formularioViewModel.cargarUsuarioPorCorreo(correo)
                }
            }
            val usuario by formularioViewModel.usuarioActual.collectAsState()

            AdminMenuScreen(
                navController = navController,
                onLoadAdminProfile = {
                    if (correo != null) {
                        formularioViewModel.cargarUsuarioPorCorreo(correo)
                    }
                },
                usuarioNombre = usuario?.nombre ?: ""
            )
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

        composable("premium_products") {
            PremiumProductsScreen(
                viewModel = premiumProductsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun CatalogoApp(
    navController: NavHostController,
    productoViewModel: ProductoViewModel,
    formularioViewModel: FormulaarioViewModel,
    premiumProductsViewModel: PremiumProductsViewModel
) {
    val context = LocalContext.current

    PantallaProductos(
        onProductClick = { id ->
            navController.navigate("productDetail/$id")
        },

        onCerrarSesionClick = {
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).edit {
                clear()
            }

            navController.navigate("login") {
                popUpTo("catalogo") { inclusive = true }
            }
        },

        onPremiumProductsClick = {
            navController.navigate("premium_products")
        },

        onPerfilClick = {
            val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val correo = prefs.getString("correo_usuario", null)

            if (correo != null) {
                formularioViewModel.cargarUsuarioPorCorreo(correo)
            }

            navController.navigate("perfil")
        },

        viewModel = productoViewModel
    )
}
