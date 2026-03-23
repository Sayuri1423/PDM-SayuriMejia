package com.example.labo1_calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.labo1_calculadora.ui.theme.Labo1calculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val calc = Calculadora("Casio", 5, 25.0)

        val suma = calc.sumar(10.0, 5.0)
        val resta = calc.restar(10.0, 5.0)
        val multi = calc.multiplicar(10.0, 5.0)
        val division = calc.dividir(10.0, 0.0)

        setContent {
            Labo1calculadoraTheme {
                MostrarResultados(suma, resta, multi, division)
            }
        }
    }
}

@Composable
fun MostrarResultados(s: Double, r: Double, m: Double, d: Double) {
    Text(
        text = """
            Suma: $s
            Resta: $r
            Multiplicación: $m
            División: $d
        """.trimIndent()
    )
}