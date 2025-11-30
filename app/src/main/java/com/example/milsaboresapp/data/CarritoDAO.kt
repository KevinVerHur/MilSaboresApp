package com.example.milsaboresapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.milsaboresapp.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDAO {

    @Query("SELECT * FROM carrito")
    fun obtenerCarrito(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(item: CartItem)

    @Delete
    suspend fun eliminar(item: CartItem)

    @Query("DELETE FROM carrito")
    suspend fun eliminarTodo()

    @Query("UPDATE carrito SET cantidad = :cantidad WHERE id = :id")
    suspend fun actualizarCantidad(id: Int, cantidad: Int)
}
