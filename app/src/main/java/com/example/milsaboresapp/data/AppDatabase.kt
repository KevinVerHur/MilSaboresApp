package com.example.milsaboresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.model.Usuario
import com.example.milsaboresapp.model.CartItem

@Database(
    entities = [Usuario::class, Producto::class, CartItem::class],
    version = 7,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsuarioDAO(): UsuarioDAO
    abstract fun ProductoDAO(): ProductoDAO
    abstract fun CarritoDAO(): CarritoDAO
}
