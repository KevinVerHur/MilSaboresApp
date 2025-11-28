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

    init {
        cargarUsuarios()
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

    fun cargarUsuarioPorId(id: Int) {
        viewModelScope.launch {
            val usuario = usuarioDAO.obtenerUsuarioPorId(id)
            _usuarioActual.value = usuario
        }
    }

    fun agregarUsuario(nombre: String, correo: String, contrasena: String, esAdmin: Boolean = false) {
        viewModelScope.launch {
            val usuario = Usuario(nombre = nombre, correo = correo, contrasena = contrasena, esAdministrador = esAdmin)
            usuarioDAO.insertarUsuario(usuario)
            cargarUsuarios()
        }
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDAO.actualizarUsuario(usuario)
            cargarUsuarios()
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDAO.eliminarUsuario(usuario)
            cargarUsuarios()
        }
    }
}
