package com.example.milsaboresapp.model

data class Producto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val category: String,
    val imageSrc: Any
)