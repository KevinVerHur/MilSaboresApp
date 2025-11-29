import com.example.milsaboresapp.data.UsuarioDAO
import com.example.milsaboresapp.model.Usuario
import com.example.milsaboresapp.ui.theme.viewModel.FormulaarioViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class FormulaarioViewModelTest {

    private lateinit var viewModel: FormulaarioViewModel
    private lateinit var dao: UsuarioDAO

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        dao = mockk(relaxed = true)
        viewModel = FormulaarioViewModel(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun agregarUsuario_insertaYcargaUsuarios() = runTest {
        val listaFake = listOf(
            Usuario(1, "Juan", "juan@example.com", "1234")
        )

        coEvery { dao.obtenerUsuarios() } returns listaFake

        viewModel.agregarUsuario("Juan", "juan@example.com", "1234")

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { dao.insertarUsuario(any()) }
        assertEquals(listaFake, viewModel.usuarios.value)
    }

    @Test
    fun cargarUsuarioPorCorreo_devuelveCorrecto() = runTest {
        val usuarioFake = Usuario(1, "María", "maria@example.com", "abcd")

        coEvery { dao.obtenerUsuarioPorCorreo("maria@example.com") } returns usuarioFake

        viewModel.cargarUsuarioPorCorreo("maria@example.com")

        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.usuarioActual.value)
        assertEquals("María", viewModel.usuarioActual.value?.nombre)
    }
}
