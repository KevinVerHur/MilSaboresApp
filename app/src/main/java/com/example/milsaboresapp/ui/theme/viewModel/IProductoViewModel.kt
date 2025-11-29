package com.example.milsaboresapp.ui.theme.viewModel

import com.example.milsaboresapp.model.Producto
import kotlinx.coroutines.flow.StateFlow

interface IProductoViewModel {
    val products: StateFlow<List<Producto>>
    fun filterByCategory(category: String)
}
