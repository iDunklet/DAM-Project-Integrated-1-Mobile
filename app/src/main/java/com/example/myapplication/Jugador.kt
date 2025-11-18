package com.example.myapplication

import UserGameData
import android.renderscript.ScriptGroup
import java.io.Serializable

class Jugador(
    val nombre: String,
    val edad: Int,
    val partidas: MutableList<UserGameData> = mutableListOf()
    ) : Serializable
