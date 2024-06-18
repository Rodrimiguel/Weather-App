package com.example.appweather.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainPage() {
    val viewModel: ClimaViewModel = viewModel(factory = ClimaViewModel.factory)
    ClimaView(
        state = viewModel.uiState,
        onAction = { intencion ->
            viewModel.ejecutar(intencion)
        }
    )
}

// Problemas View Model en CLima Page para corregir.