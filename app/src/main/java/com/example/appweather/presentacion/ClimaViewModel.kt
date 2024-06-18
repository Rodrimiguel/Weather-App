package com.example.appweather.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.istea.AppWeather.repository.Repositorio
import com.istea.AppWeather.repository.RepositorioApi
import com.istea.AppWeather.repository.modelos.Ciudad
import kotlinx.coroutines.launch

class ClimaViewModel(
    val respositorio: Repositorio
) : ViewModel() {

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repositorio = RepositorioApi()
                ClimaViewModel(repositorio)
            }
        }
    }

    var uiState by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) { // viem model
        when (intencion) {
            ClimaIntencion.BorrarTodo -> borrarTodo()
            ClimaIntencion.MostrarCaba -> mostrarCaba()
            ClimaIntencion.MostrarCordoba -> mostrarCordoba()
            ClimaIntencion.MostrarError -> mostrarError()
        }
    }

    private fun mostrarError() {
        uiState = ClimaEstado.Error("este es un error de mentiras")
    }

    private fun borrarTodo() {
        uiState = ClimaEstado.Vacio
    }

    private fun mostrarCaba() {

    }

    private fun mostrarCordoba() {
        ClimaEstado.Cargando
        viewModelScope.launch {
            val cordoba = Ciudad(name = "Cordoba", lat = -31.4135, lon = -64.18105, state = "Ar")
            try {
                val clima = respositorio.traerClima(cordoba)
                ClimaEstado.Exitoso(
                    ciudad = clima.name,
                    temperatura = 10.0,
                    descripcion = "-|-",
                    st = 10.2
                )
            } catch (exeption: Exception) {
                ClimaEstado.Error("-- Bug --")
            }
        }
    }
}




