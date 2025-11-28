package com.example.milsaboresapp.data

import com.example.milsaboresapp.model.Producto

class RepositorioProductos(private val dao: ProductoDAO) {

    suspend fun obtenerProductos(): List<Producto> =
        dao.obtenerProductos()

    suspend fun obtenerProductoPorId(id: Int): Producto? =
        dao.obtenerProductoPorId(id)

    suspend fun insertarProducto(producto: Producto) =
        dao.insertarProducto(producto)

    suspend fun actualizarProducto(producto: Producto) =
        dao.actualizarProducto(producto)

    suspend fun eliminarProducto(producto: Producto) =
        dao.eliminarProducto(producto)
}
