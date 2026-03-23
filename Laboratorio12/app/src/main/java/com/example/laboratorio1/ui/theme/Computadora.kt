package com.example.laboratorio1.ui.theme

class Computadora(
    var ram: Int,
    var almacenamiento: Int,
    var sistemaOperativo: String,
    var programasInstalados: List<String>
) {
    fun encender() {
        println("La computadora está encendida 💻")
    }

    fun apagar() {
        println("La computadora está apagada 📴")
    }

    fun actualizarRam(nuevaRam: Int) {
        ram = nuevaRam
    }

    fun actualizarAlmacenamiento(nuevoEspacio: Int) {
        almacenamiento = nuevoEspacio
    }

    fun cambiarSistema(nuevoSO: String) {
        sistemaOperativo = nuevoSO
    }

    fun programasActuales(): List<String> {
        return programasInstalados.filter { it.contains("2026") }
    }
}