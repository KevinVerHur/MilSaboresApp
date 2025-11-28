package com.example.milsaboresapp.data

import androidx.room.*
import com.example.milsaboresapp.model.Producto

@Dao
interface ProductoDAO {

    @Query("SELECT * FROM productos")
    suspend fun obtenerProductos(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    @Insert
    suspend fun insertarProducto(producto: Producto)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)
}
