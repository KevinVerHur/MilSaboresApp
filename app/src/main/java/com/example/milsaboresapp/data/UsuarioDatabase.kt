package com.example.milsaboresapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.milsaboresapp.model.Usuario

@Database(entities = [Usuario::class], version = 2)
abstract class UsuarioDatabase : RoomDatabase() {

    abstract fun  UsuarioDAO(): UsuarioDAO



}