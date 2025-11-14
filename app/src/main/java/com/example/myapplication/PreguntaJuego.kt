package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.io.Serializable

data class PreguntaJuego(
    val id: Int,
    val categoria: String,
    val enunciado_es: String,
    val respuesta_correcta_en: String,
    val opciones_en: List<String>
                        ) : Serializable {
    companion object {
        fun loadQuestionsFromJson(context: Context, fileName: String): List<PreguntaJuego> {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val gson = com.google.gson.Gson()
            val type = com.google.gson.reflect.TypeToken.getParameterized(List::class.java, PreguntaJuego::class.java).type
            return gson.fromJson(jsonString, type)
        }
    }
}