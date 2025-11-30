import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.data.CarritoDAO
import com.example.milsaboresapp.model.CartItem
import com.example.milsaboresapp.model.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val dao: CarritoDAO) : ViewModel() {

    val carrito: StateFlow<List<CartItem>> =
        dao.obtenerCarrito().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val total: StateFlow<Int> =
        carrito.map { items ->
            items.sumOf { it.precio * it.cantidad }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    fun agregarProductoAlCarrito(producto: Producto, cantidad: Int) {
        viewModelScope.launch {
            dao.insertar(
                CartItem(
                    productoId = producto.id,
                    nombre = producto.nombre,
                    precio = producto.precio,
                    imagenUrl = producto.imagenUrl,
                    cantidad = cantidad
                )
            )
        }
    }

    fun eliminarItem(item: CartItem) {
        viewModelScope.launch {
            dao.eliminar(item)
        }
    }

    fun actualizarCantidad(item: CartItem, cantidad: Int) {
        viewModelScope.launch {
            dao.actualizarCantidad(item.id, cantidad)
        }
    }
}