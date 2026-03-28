package com.example.laboratorio2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.laboratorio2.ui.theme.Laboratorio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        app()
                    }
                }
            }
        }
    }
}

@Composable
fun app() {


    val pastelFondo = Color(0xFFFF80AB)
    val pastelBorde = Color(0xFF9575CD)
    val pastelBoton = Color(0xFF5C6BC0)
    val pastelCaja = Color(0xFFE2F0CB)

    var usurious by remember { mutableStateOf("") }
    val userlist = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelFondo)
            .border(2.dp, pastelBorde)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        TextField(
            label = { Text("Nombre") },
            value = usurious,
            onValueChange = {
                usurious = it
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = pastelBoton
            ),
            onClick = {
                if (usurious.isNotBlank()) {
                    userlist.add(usurious)
                    usurious = ""
                }
            }
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Listado de nombres:")

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = pastelBoton
                ),
                onClick = {
                    userlist.clear()
                }
            ) {
                Text("Limpiar")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(pastelCaja)
                .border(2.dp, pastelBorde)
        ) {
            LazyColumn {
                items(userlist.size) { index ->
                    Text(
                        text = "${index + 1}. ${userlist[index]}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}