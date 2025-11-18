package com.example.myapplication

import UserGameData
import java.io.Serializable

val jugadores = mutableListOf<Jugador>()

class Jugador(
    val nombre: String,
    val edad: Int,
    var partidas: MutableList<UserGameData> = mutableListOf()
) : Serializable
