package com.example.milsaboresapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val nombre: String,
    val precio: Int,
    val imagenUrl: String?,
    val cantidad: Int = 1
)