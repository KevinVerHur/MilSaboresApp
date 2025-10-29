package com.example.milsaboresapp.ui.theme.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SelectorImagenViewModel : ViewModel() {
    var uriImagen by mutableStateOf<String?>(null)
        private set

    var botonPresionado by mutableStateOf(false)
        private set

    fun asignarUriImagen(uri: String?) {
        uriImagen = uri
    }

    fun marcarBotonPresionado() {
        botonPresionado = true
    }
}
