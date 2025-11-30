package com.example.milsaboresapp.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.milsaboresapp.FakeProductoViewModel
import org.junit.Rule
import org.junit.Test

class PantallaProductoScreenTest {

    @get:Rule val composeTestRule = createComposeRule()

    @Test
    fun pantallaProductos_muestraTitulo() {

        val fakeVm = FakeProductoViewModel()

        composeTestRule.setContent {
            PantallaProductos(
                onProductClick = {},
                onPerfilClick = {},
                onCerrarSesionClick = {},
                onPremiumProductsClick = {},
                onCarritoClick = {},
                viewModel = fakeVm
            )
        }

        composeTestRule.onNodeWithText("NUESTROS PRODUCTOS").assertIsDisplayed()
    }
}
