package com.example.laboratorio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.example.laboratorio1.ui.theme.Computadora
import com.example.laboratorio1.ui.theme.Laboratorio1Theme
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val programas = listOf(
            "Whatssap 2026",
            "minecraft 2026",
            "Android Studio 2022"
        )

        val pc = Computadora(8, 256, "Windows 10", programas)

        val actuales = pc.programasActuales()

        setContent {
            MaterialTheme {
                MostrarProgramas(actuales)
            }
        }
    }
}


@Composable
fun MostrarProgramas(lista: List<String>) {
    val texto = lista.joinToString("\n")

    Text(
        text = "Programas creados en 2026:\n$texto"
    )
}