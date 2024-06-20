package com.example.appweather.presentacion.clima.actual

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appweather.repository.Repositorio
import com.example.appweather.router.Router
import kotlinx.coroutines.launch

class ClimaViewModel(
    val repositorio: Repositorio
) : ViewModel() {

    var uiState by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) { // viem model
        when (intencion) {
            ClimaIntencion.actualizarClima -> traerClima()
        }
    }

    fun traerClima() {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val clima = repositorio.traerClima(lat = lat, lon = lon)
                uiState = ClimaEstado.Exitoso(
                    ciudad = clima.name,
                    temperatura = clima.main.temp,
                    descripcion = clima.weather.first().description,
                    st = clima.main.feels_like,
                )
            } catch (exception: Exception) {
                uiState = ClimaEstado.Error(exception.localizedMessage ?: "error desconocido")
            }
        }
    }

}

class ClimaViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val lat: Float,
    private val lon: Float,
    private val nombre: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            return ClimaViewModel(repositorio, router, lat, lon, nombre) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


