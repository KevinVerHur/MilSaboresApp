package com.example.milsaboresapp.ui.theme.screen

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.milsaboresapp.R
import com.example.milsaboresapp.ui.theme.viewModel.SelectorImagenViewModel

// 🧠 Función auxiliar para guardar imágenes en la galería evitando duplicados
fun guardarImagenEnGaleria(context: Context, resId: Int, nombreArchivo: String) {
    val drawable = context.getDrawable(resId)

    if (drawable is BitmapDrawable) {
        val bitmap = drawable.bitmap
        val resolver = context.contentResolver

        // 🕵️ Buscar si ya existe una imagen con el mismo nombre
        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(nombreArchivo)

        resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                Log.i("GuardarImagen", "⚠️ $nombreArchivo ya existe, no se guardará de nuevo.")
                return // 🚫 No guardar duplicado
            }
        }

        // 📝 Si no existe, crear el nuevo registro
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, nombreArchivo)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MilSaboresApp")
            }
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                resolver.openOutputStream(it)?.use { stream ->
                    bitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, stream)
                }
                Log.i("GuardarImagen", "✅ Imagen guardada: $nombreArchivo")
            } catch (e: Exception) {
                Log.e("GuardarImagen", "❌ Error guardando $nombreArchivo: ${e.message}")
            }
        } ?: Log.e("GuardarImagen", "❌ No se pudo insertar en MediaStore.")
    } else {
        Log.e("GuardarImagen", "❌ Drawable inválido para el recurso $resId")
    }
}

// 🖼️ Pantalla principal del selector de imagen
@Composable
fun PantallaSelectorImagen(
    viewModel: SelectorImagenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    // 🔹 Estado para no repetir guardado dentro de esta sesión
    var imagenesGuardadas by remember { mutableStateOf(false) }

    // 🔹 Lanzador para seleccionar una imagen de la galería
    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.asignarUriImagen(uri?.toString())
    }

    // 🔹 Lanzador para solicitar permisos (Android 13+)
    val lanzadorPermisos = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permisoConcedido ->
        if (permisoConcedido) {
            guardarImagenesSiNoGuardadas(context, imagenesGuardadas) { imagenesGuardadas = it }
        } else {
            Log.e("Permiso", "❌ Permiso para leer imágenes no concedido.")
        }
    }

    Column {
        Button(onClick = {
            viewModel.marcarBotonPresionado()
            lanzadorGaleria.launch("image/*")

            // ✅ Verificar permisos antes de guardar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                lanzadorPermisos.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                guardarImagenesSiNoGuardadas(context, imagenesGuardadas) { imagenesGuardadas = it }
            }
        }) {
            Text("Seleccionar Imagen")
        }

        // 🔹 Mostrar imágenes solo si se presionó el botón
        if (viewModel.botonPresionado) {
            // Aquí podrías mostrar tus Image() o AsyncImage()
        }
    }
}

// 🔧 Función auxiliar que evita guardar más de una vez
private fun guardarImagenesSiNoGuardadas(
    context: Context,
    yaGuardadas: Boolean,
    onGuardadas: (Boolean) -> Unit
) {
    if (!yaGuardadas) {
        guardarImagenEnGaleria(context, R.drawable.tiramisu, "tiramisu.jpg")
        guardarImagenEnGaleria(context, R.drawable.torta_boda, "torta_boda.jpg")
        guardarImagenEnGaleria(context, R.drawable.empanada_manzana, "empanada_manzana.jpg")
        guardarImagenEnGaleria(context, R.drawable.galletas_avena_veganas, "galletas_avena_veganas.jpg")
        guardarImagenEnGaleria(context, R.drawable.mousse_chocolate, "mousse_chocolate.jpg")
        guardarImagenEnGaleria(context, R.drawable.pan_sin_gluten, "pan_sin_gluten.jpg")
        guardarImagenEnGaleria(context, R.drawable.torta_manjar, "torta_manjar.jpg")
        guardarImagenEnGaleria(context, R.drawable.torta_naranja_sin_azucar, "torta_naranja_sin_azucar.jpg")
        guardarImagenEnGaleria(context, R.drawable.torta_santiago, "torta_santiago.jpg")
        guardarImagenEnGaleria(context, R.drawable.kike, "kike.jpg")
        onGuardadas(true)
    } else {
        Log.i("GuardarImagen", "⚠️ Las imágenes ya fueron guardadas.")
    }
}
