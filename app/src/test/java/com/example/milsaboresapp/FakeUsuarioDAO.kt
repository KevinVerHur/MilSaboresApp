package com.example.milsaboresapp

import com.example.milsaboresapp.data.UsuarioDAO
import com.example.milsaboresapp.model.Usuario

class FakeUsuarioDAO : UsuarioDAO {

    private val listaInterna = mutableListOf<Usuario>()
    private var idAuto = 1

    override suspend fun insertarUsuario(usuario: Usuario) {
        val nuevo = usuario.copy(id = idAuto++)
        listaInterna.add(nuevo)
    }

    override suspend fun obtenerUsuarios(): List<Usuario> {
        return listaInterna.toList()
    }

    override suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? {
        return listaInterna.find { it.correo == correo }
    }

    override suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return listaInterna.find { it.id == id }
    }

    override suspend fun actualizarUsuario(usuario: Usuario) {
        val index = listaInterna.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            listaInterna[index] = usuario
        }
    }

    override suspend fun eliminarUsuario(usuario: Usuario) {
        listaInterna.removeIf { it.id == usuario.id }
    }

    override suspend fun obtenerAdministradores(): List<Usuario> {
        return listaInterna.filter { it.esAdministrador }
    }
}
