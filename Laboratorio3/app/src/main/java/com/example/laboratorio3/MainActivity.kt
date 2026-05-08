package com.example.laboratorio3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.laboratorio3.ui.theme.Laboratorio3Theme
import kotlin.math.abs

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Laboratorio3Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("lista") {
            PantallaLista(navController)
        }

        composable("sensor") {
            PantallaSensor(navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {

    val fondo = Color(0xFFFF80AB)
    val colorBoton = Color(0xFF5C6BC0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "HOME",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate("lista")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorBoton
            )
        ) {
            Text("Ver lista de nombres")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("sensor")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorBoton
            )
        ) {
            Text("Ver sensores")
        }
    }
}

@Composable
fun PantallaLista(navController: NavController) {

    val pastelFondo = Color(0xFFFFC1E3)
    val pastelBoton = Color(0xFF5C6BC0)

    var usuario by remember { mutableStateOf("") }

    val lista = remember {
        mutableStateListOf<String>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelFondo)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Lista de Usuarios",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = usuario,
            onValueChange = {
                usuario = it
            },
            label = {
                Text("Nombre")
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                if (usuario.isNotBlank()) {
                    lista.add(usuario)
                    usuario = ""
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = pastelBoton
            )
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate("home")
            }
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            items(lista) { nombre ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Text(
                        text = nombre,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaSensor(navController: NavController) {

    val context = LocalContext.current

    val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val acelerometro =
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {

                val alpha = 0.8f

                val newX = event?.values?.get(0) ?: 0f
                val newY = event?.values?.get(1) ?: 0f
                val newZ = event?.values?.get(2) ?: 0f

                // suaviza el movimiento
                x = alpha * x + (1 - alpha) * newX
                y = alpha * y + (1 - alpha) * newY
                z = alpha * z + (1 - alpha) * newZ

                // elimina movimientos mínimos
                if (abs(x) < 0.05f) x = 0f
                if (abs(y) < 0.05f) y = 0f
                if (abs(z) < 0.05f) z = 0f
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) {
            }
        }

        sensorManager.registerListener(
            listener,
            acelerometro,
            500000
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    val fondoSensor = Color(0xFF1E1E2F)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoSensor)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sensor de Movimiento",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF3949AB)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "X = %.2f".format(x),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Y = %.2f".format(y),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Z = %.2f".format(z),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("home")
            }
        ) {
            Text("Volver")
        }
    }
}