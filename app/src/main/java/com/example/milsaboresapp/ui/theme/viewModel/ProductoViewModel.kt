package com.example.milsaboresapp.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.data.RepositorioProductos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductoViewModel(
    private val repositorioProductos: RepositorioProductos
) : ViewModel(), IProductoViewModel {

    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    override val products: StateFlow<List<Producto>> = _products

    private val _productoActual = MutableStateFlow<Producto?>(null)
    val productoActual: StateFlow<Producto?> = _productoActual

    private var productosOriginales: List<Producto> = emptyList()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            val lista = repositorioProductos.obtenerProductos()

            if (lista.isEmpty()) {
                val productosBase = listOf(
                    Producto(
                        nombre = "Cheesecake Sin Azúcar",
                        descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
                        categoria = "Productos Sin Azúcar",
                        precio = 47000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/cheesecake_sin_azucar"
                    ),
                    Producto(
                        nombre = "Tarta de Santiago",
                        descripcion = "Tradicional tarta española hecha con almendras, azúcar y huevos.",
                        categoria = "Pastelería Tradicional",
                        precio = 6000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_santiago"
                    ),
                    Producto(
                        nombre = "Tiramisú Clásico",
                        descripcion = "Postre italiano individual con capas de café, mascarpone y cacao.",
                        categoria = "Postres Individuales",
                        precio = 5500,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/tiramisu"
                    ),
                    Producto(
                        nombre = "Torta Circular de Manjar",
                        descripcion = "Torta tradicional chilena con manjar y nueces.",
                        categoria = "Tortas Circulares",
                        precio = 42000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_manjar"
                    ),
                    Producto(
                        nombre = "Galletas Veganas de Avena",
                        descripcion = "Crujientes y sabrosas, una excelente opción para un snack saludable.",
                        categoria = "Productos Veganos",
                        precio = 4500,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/galletas_avena_veganas"
                    ),
                    Producto(
                        nombre = "Empanada de Manzana",
                        descripcion = "Pastelería tradicional rellena de manzanas especiadas.",
                        categoria = "Pastelería Tradicional",
                        precio = 3000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/empanada_manzana"
                    ),
                    Producto(
                        nombre = "Mousse de Chocolate",
                        descripcion = "Postre individual cremoso hecho con chocolate de alta calidad.",
                        categoria = "Postres Individuales",
                        precio = 5000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/mousse_chocolate"
                    ),
                    Producto(
                        nombre = "Torta Especial de Boda",
                        descripcion = "Elegante y deliciosa, diseñada para destacar en cualquier boda.",
                        categoria = "Tortas Especiales",
                        precio = 60000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_boda"
                    ),
                    Producto(
                        nombre = "Torta Sin Azúcar de Naranja",
                        descripcion = "Torta ligera, endulzada naturalmente, ideal para quienes buscan opciones saludables.",
                        categoria = "Productos Sin Azúcar",
                        precio = 48000,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_naranja_sin_azucar"
                    ),
                    Producto(
                        nombre = "Pan Sin Gluten",
                        descripcion = "Suave y esponjoso, ideal para acompañar cualquier comida.",
                        categoria = "Productos Sin Gluten",
                        precio = 3500,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/pan_sin_gluten"
                    )
                )

                productosBase.forEach { producto ->
                    repositorioProductos.insertarProducto(producto)
                }
            }

            val nuevosProductos = repositorioProductos.obtenerProductos()
            productosOriginales = nuevosProductos
            _products.value = nuevosProductos
        }
    }

    fun cargarProductoPorId(id: Int) {
        viewModelScope.launch {
            _productoActual.value = repositorioProductos.obtenerProductoPorId(id)
        }
    }

    fun insertarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorioProductos.insertarProducto(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorioProductos.actualizarProducto(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repositorioProductos.eliminarProducto(producto)
            cargarProductos()
        }
    }

    override fun filterByCategory(category: String) {
        _products.value = if (category == "all") productosOriginales
        else productosOriginales.filter { it.categoria == category }
    }

    fun getProductById(id: Int): Producto? {
        return productosOriginales.find { it.id == id }
    }
}
