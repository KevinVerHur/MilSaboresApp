package com.example.milsaboresapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.milsaboresapp.model.Usuario

@Dao
interface UsuarioDAO {

    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    @Update
    suspend fun actualizarUsuario(usuario: Usuario)

    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    suspend fun obtenerUsuarios(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE correo = :correo")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun obtenerUsuarioPorId(id: Int): Usuario?

    @Query("SELECT * FROM Usuario WHERE esAdministrador = 1")
    suspend fun obtenerAdministradores(): List<Usuario>
}
