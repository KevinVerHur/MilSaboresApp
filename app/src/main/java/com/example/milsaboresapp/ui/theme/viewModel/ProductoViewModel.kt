package com.example.milsaboresapp.ui.theme.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.data.EntidadProducto
import com.example.milsaboresapp.data.RepositorioProductos

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.filter
import kotlin.collections.find

class ProductoViewModel(
    private val repositorioProductos: RepositorioProductos
) : ViewModel() {

    private val _products = MutableStateFlow<List< EntidadProducto>>(emptyList())
    val products: StateFlow<List<EntidadProducto>> = _products

    private var productosOriginales = emptyList<EntidadProducto>()

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            val lista = repositorioProductos.obtenerProductos()

            if (lista.isEmpty()) {
                val productosBase = listOf(
                    EntidadProducto(
                        nombre = "Cheesecake Sin Azúcar",
                        descripcion = "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
                        categoria = "Productos Sin Azúcar",
                        precio = 47000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/cheesecake_sin_azucar"
                    ),
                    EntidadProducto(
                        nombre = "Tarta de Santiago",
                        descripcion = "Tradicional tarta española hecha con almendras, azúcar y huevos.",
                        categoria = "Pastelería Tradicional",
                        precio = 6000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_santiago"
                    ),
                    EntidadProducto(
                        nombre = "Tiramisú Clásico",
                        descripcion = "Postre italiano individual con capas de café, mascarpone y cacao.",
                        categoria = "Postres Individuales",
                        precio = 5500.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/tiramisu"
                    ),
                    EntidadProducto(
                        nombre = "Torta Circular de Manjar",
                        descripcion = "Torta tradicional chilena con manjar y nueces.",
                        categoria = "Tortas Circulares",
                        precio = 42000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_manjar"
                    ),
                    EntidadProducto(
                        nombre = "Galletas Veganas de Avena",
                        descripcion = "Crujientes y sabrosas, una excelente opción para un snack saludable.",
                        categoria = "Productos Veganos",
                        precio = 4500.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/galletas_avena_veganas"
                    ),
                    EntidadProducto(
                        nombre = "Empanada de Manzana",
                        descripcion = "Pastelería tradicional rellena de manzanas especiadas.",
                        categoria = "Pastelería Tradicional",
                        precio = 3000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/empanada_manzana"
                    ),
                    EntidadProducto(
                        nombre = "Mousse de Chocolate",
                        descripcion = "Postre individual cremoso hecho con chocolate de alta calidad.",
                        categoria = "Postres Individuales",
                        precio = 5000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/mousse_chocolate"
                    ),
                    EntidadProducto(
                        nombre = "Torta Especial de Boda",
                        descripcion = "Elegante y deliciosa, diseñada para destacar en cualquier boda.",
                        categoria = "Tortas Especiales",
                        precio = 60000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_boda"
                    ),
                    EntidadProducto(
                        nombre = "Torta Sin Azúcar de Naranja",
                        descripcion = "Torta ligera, endulzada naturalmente, ideal para quienes buscan opciones saludables.",
                        categoria = "Productos Sin Azúcar",
                        precio = 48000.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/torta_naranja_sin_azucar"
                    ),
                    EntidadProducto(
                        nombre = "Pan Sin Gluten",
                        descripcion = "Suave y esponjoso, ideal para acompañar cualquier comida.",
                        categoria = "Productos Sin Gluten",
                        precio = 3500.0,
                        imagenUrl = "android.resource://com.example.milsaboresapp/drawable/pan_sin_gluten"
                    )
                )
                productosBase.forEach { producto ->
                    repositorioProductos.insertarProducto(producto)
                }
            }

            _products.value = repositorioProductos.obtenerProductos()
        }
    }


    fun filterByCategory(category: String) {
        _products.value = if (category == "all") productosOriginales
        else productosOriginales.filter { it.categoria == category }
    }

    fun getProductById(id: Int): EntidadProducto? {
        return productosOriginales.find { it.id == id }
    }
}
