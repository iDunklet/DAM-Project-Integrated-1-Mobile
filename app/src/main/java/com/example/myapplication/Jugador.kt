package com.example.myapplication

import UserGameData
import android.renderscript.ScriptGroup
import java.io.Serializable

val jugadores = mutableListOf<Jugador>()

class Jugador(
    val nombre: String,
    val edad: Int,
    val userGameData: UserGameData
) : Serializable
