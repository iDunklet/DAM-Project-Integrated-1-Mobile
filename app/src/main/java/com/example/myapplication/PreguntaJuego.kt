package com.example.myapplication

import android.content.Context
import java.io.Serializable

data class PreguntaJuego(
    val id: Int,
    val categoria: String,
    val enunciadoEs: String,
    val respuestaCorrectaEn: String,
    val opcionesEn: List<String>
) : Serializable {
    companion object {
        fun loadQuestionsFromJson(context: Context, fileName: String): List<PreguntaJuego> {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val gson = com.google.gson.Gson()
            val type = com.google.gson.reflect.TypeToken.getParameterized(
                List::class.java,
                PreguntaJuego::class.java
            ).type
            return gson.fromJson(jsonString, type)
        }
    }
}