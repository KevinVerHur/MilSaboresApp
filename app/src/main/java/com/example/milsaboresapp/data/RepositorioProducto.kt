package com.example.milsaboresapp.data

import com.example.milsaboresapp.R
import com.example.milsaboresapp.model.Producto

class RepositorioProducto {

    fun obtenerProductos(): List<Producto> = listOf(
        Producto(
            1,
            "Cheesecake Sin Azúcar",
            "Suave y cremoso, este cheesecake es una opción perfecta para disfrutar sin culpa.",
            47000,
            "Productos Sin Azúcar",
            R.drawable.cheesecake_sin_azucar
        ),
        Producto(
            2,
            "Tarta de Santiago",
            "Tradicional tarta española hecha con almendras, azúcar y huevos, una delicia para los amantes de los postres clásicos.",
            6000,
            "Pastelería Tradicional",
            R.drawable.torta_santiago
        ),
        Producto(
            3,
            "Tiramisú Clásico",
            "Un postre italiano individual con capas de café, mascarpone y cacao, perfecto para finalizar cualquier comida.",
            5500,
            "Postres Individuales",
            R.drawable.tiramisu
        ),
        Producto(
            4,
            "Torta Circular de Manjar",
            "Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores dulces y clásicos.",
            42000,
            "Tortas Circulares",
            R.drawable.torta_manjar
        ),
        Producto(
            5,
            "Galletas Veganas de Avena",
            "Crujientes y sabrosas, estas galletas son una excelente opción para un snack saludable y vegano.",
            4500,
            "Productos Veganos",
            R.drawable.galletas_avena_veganas
        ),
        Producto(
            6,
            "Empanada de Manzana",
            "Pastelería tradicional rellena de manzanas especiadas, perfecta para un dulce desayuno o merienda.",
            3000,
            "Pastelería Tradicional",
            R.drawable.empanada_manzana
        ),
        Producto(
            7,
            "Mousse de Chocolate",
            "Postre individual cremoso y suabe, hecho con chocolate de alta calidad, ideal para los amantes del chocolate.",
            5000,
            "Postres Individuales",
            R.drawable.mousse_chocolate
        ),
        Producto(
            8,
            "Torta Especial de Boda",
            "Elegante y deliciosa, esta torta está diseñada para ser el centro de atención en cualquier boda.",
            60000,
            "Tortas Especiales",
            R.drawable.torta_boda
        ),
        Producto(
            9,
            "Torta Sin Azúcar de Naranja",
            "Torta ligera y deliciosa, endulzada naturalmente, ideal para quienes buscan opciones más saludables.",
            48000,
            "Productos Sin Azúcar",
            R.drawable.torta_naranja_sin_azucar
        ),
        Producto(
            10,
            "Pan Sin Gluten",
            "Suave y esponjoso, ideal para sándwiches o para acompañar cualquier comida.",
            3500,
            "Productos Sin Gluten",
            R.drawable.pan_sin_gluten
        )
    )
}