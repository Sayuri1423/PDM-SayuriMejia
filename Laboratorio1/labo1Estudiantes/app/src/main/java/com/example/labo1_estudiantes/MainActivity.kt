package com.example.labo1_estudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.labo1_estudiantes.Estudiante
import com.example.labo1_estudiantes.ui.theme.Labo1EstudiantesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val ciclo01 = listOf(
            Estudiante("Antonella", "003561", "Programación de Dispositivos Móviles"),
            Estudiante("Lucas", "0024562", "Programación de Dispositivos Móviles"),
            Estudiante("Gustavo", "0035463", "Programación de Dispositivos Móviles"),

            Estudiante("Maria", "0046574", "Análisis Numérico"),
            Estudiante("Penelope", "006673225", "Análisis Numérico"),
            Estudiante("Sofia", "00656673", "Análisis Numérico"),
            Estudiante("Dimas", "00765325", "Análisis Numérico")
        )
        val moviles = ciclo01.filter {
            it.asignatura == "Programación de Dispositivos Móviles : "
        }

        setContent {
            Labo1EstudiantesTheme{
                MostrarEstudiantes(moviles)
            }
        }
    }
}

@Composable
fun MostrarEstudiantes(lista: List<Estudiante>) {

    val texto = lista.joinToString("\n") {
        "${it.nombre} - ${it.carnet}"
    }

    Text(
        text = "Estudiantes de Dispositivos Móviles:\n$texto"
    )
}