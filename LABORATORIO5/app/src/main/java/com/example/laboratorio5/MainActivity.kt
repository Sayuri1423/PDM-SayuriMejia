package com.example.laboratorio5

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Tarea(
    val titulo: String,
    val descripcion: String
)

fun guardarTareas(
    context: Context,
    tareas: List<Tarea>
) {

    val prefs = context.getSharedPreferences(
        "MIS_TAREAS",
        Context.MODE_PRIVATE
    )

    val json = Gson().toJson(tareas)

    prefs.edit()
        .putString("lista_tareas", json)
        .apply()
}

fun cargarTareas(
    context: Context
): MutableList<Tarea> {

    val prefs = context.getSharedPreferences(
        "MIS_TAREAS",
        Context.MODE_PRIVATE
    )

    val json = prefs.getString(
        "lista_tareas",
        null
    )

    return if (json != null) {

        val tipo =
            object : TypeToken<MutableList<Tarea>>() {}.type

        Gson().fromJson(json, tipo)

    } else {

        mutableListOf()
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current

            val navController = rememberNavController()

            val listaTareas = remember {

                mutableStateListOf<Tarea>().apply {

                    addAll(
                        cargarTareas(context)
                    )
                }
            }

            NavHost(
                navController = navController,
                startDestination = "inicio"
            ) {

                composable("inicio") {
                    PantallaInicio(navController)
                }

                composable("tareas") {
                    PantallaTareas(
                        navController,
                        listaTareas
                    )
                }

                composable("agregar") {
                    PantallaAgregar(
                        navController,
                        listaTareas
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(
    navController: NavHostController
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(
            onClick = {
                navController.navigate("tareas")
            }
        ) {
            Text("Siguiente")
        }
    }
}

@Composable
fun PantallaTareas(
    navController: NavHostController,
    tareas: List<Tarea>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis Tareas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {
                navController.navigate("agregar")
            }
        ) {
            Text("Agregar Nueva Tarea")
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(tareas) { tarea ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = tarea.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = tarea.descripcion
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}

@Composable
fun PantallaAgregar(
    navController: NavHostController,
    tareas: SnapshotStateList<Tarea>
) {

    val context = LocalContext.current

    var titulo by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Nueva Tarea",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {

                if (titulo.isNotBlank()) {

                    tareas.add(
                        Tarea(
                            titulo,
                            descripcion
                        )
                    )

                    guardarTareas(
                        context,
                        tareas
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}