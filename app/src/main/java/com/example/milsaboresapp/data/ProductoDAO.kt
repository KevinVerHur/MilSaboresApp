package com.example.milsaboresapp.data

import androidx.room.*

@Dao
interface ProductoDAO {

    @Query("SELECT * FROM productos")
    suspend fun obtenerProductos(): List<EntidadProducto>

    @Insert
    suspend fun insertarProducto(producto: EntidadProducto)

    @Update
    suspend fun actualizarProducto(producto: EntidadProducto)

    @Delete
    suspend fun eliminarProducto(producto: EntidadProducto)
}
