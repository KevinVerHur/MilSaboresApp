package com.example.milsaboresapp.data

class RepositorioProductos(private val dao: ProductoDAO) {

    suspend fun obtenerProductos(): List<EntidadProducto> =
        dao.obtenerProductos()

    suspend fun insertarProducto(producto: EntidadProducto) =
        dao.insertarProducto(producto)

    suspend fun actualizarProducto(producto: EntidadProducto) =
        dao.actualizarProducto(producto)

    suspend fun eliminarProducto(producto: EntidadProducto) =
        dao.eliminarProducto(producto)
}
