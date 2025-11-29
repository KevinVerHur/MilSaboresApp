package com.example.milsaboresapp

import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.ui.theme.viewModel.IProductoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeProductoViewModel : IProductoViewModel {

    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    override val products: StateFlow<List<Producto>> = _products.asStateFlow()

    fun setProductos(lista: List<Producto>) {
        _products.value = lista
    }

    override fun filterByCategory(category: String) {
        // No necesitamos lógica real para este test
    }
}
