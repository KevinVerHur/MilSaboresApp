package com.example.milsaboresapp.ui.theme.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.data.RepositorioProducto

import com.example.milsaboresapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.filter
import kotlin.collections.find

class ProductoViewModel : ViewModel() {

    private val repositorio = RepositorioProducto()

    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    val products: StateFlow<List<Producto>> = _products

    private val _selectedCategory = MutableStateFlow("all")
    val selectedCategory: StateFlow<String> = _selectedCategory

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _products.value = repositorio.obtenerProductos()
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        _products.value = if (category == "all") {
            repositorio.obtenerProductos()
        } else {
            repositorio.obtenerProductos().filter { it.category == category }
        }
    }

    fun getProductById(id: Int): Producto? {
        return repositorio.obtenerProductos().find { it.id == id }
    }
}
