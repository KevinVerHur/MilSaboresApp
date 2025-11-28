package com.example.milsaboresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.milsaboresapp.model.Producto
import com.example.milsaboresapp.model.Usuario

@Database(entities = [Usuario::class, Producto::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun  UsuarioDAO(): UsuarioDAO
    abstract fun ProductoDAO(): ProductoDAO

}