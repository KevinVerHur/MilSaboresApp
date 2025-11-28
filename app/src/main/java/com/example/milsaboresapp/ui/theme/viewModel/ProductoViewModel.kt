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
            productosOriginales = repositorioProductos.obtenerProductos()
            _products.value = productosOriginales
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
