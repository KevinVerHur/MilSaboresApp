package com.example.milsaboresapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.milsaboresapp.model.Usuario


@Dao
interface UsuarioDAO {
    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    suspend fun obtenerUsuarios(): List<Usuario>
}