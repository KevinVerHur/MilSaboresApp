package com.example.milsaboresapp.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.data.UsuarioDAO
import com.example.milsaboresapp.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormulaarioViewModel(private val usuarioDAO: UsuarioDAO) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual = _usuarioActual.asStateFlow()

    fun agregarUsuario(nombre: String, correo: String, contrasena: String) {
        viewModelScope.launch {
            val usuario = Usuario(
                nombre = nombre,
                correo = correo,
                contrasena = contrasena
            )
            usuarioDAO.insertarUsuario(usuario)
            cargarUsuarios()
        }
    }

    fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = usuarioDAO.obtenerUsuarios()
        }
    }

    fun cargarUsuarioPorCorreo(correo: String) {
        viewModelScope.launch {
            val usuario = usuarioDAO.obtenerUsuarioPorCorreo(correo)
            _usuarioActual.value = usuario
        }
    }
}
