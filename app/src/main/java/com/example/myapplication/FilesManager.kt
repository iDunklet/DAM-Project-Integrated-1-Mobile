package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileReader
import java.io.FileWriter

class FilesManager {
    companion object
    {
        fun readFile(context: Context): MutableList<Jugador>
        {
            val jsonFilePath = context.filesDir.toString() + "/json/colorsGame.json"
            val jsonFile = FileReader(jsonFilePath)
            val listJugadorsType = object : TypeToken<MutableList<Jugador>>() {}.type
            val jugadors: MutableList<Jugador> = Gson().fromJson(jsonFile, listJugadorsType)
            return jugadors
        }

        fun saveFile(context: Context, jugadores: List<Jugador>)
        {
            val jsonFilePath = context.filesDir.toString() + "/json/colorsGame.json"
            val jsonFile = FileWriter(jsonFilePath)
            val gson = Gson()
            val jsonElement = gson.toJson(jugadores)
            jsonFile.write(jsonElement)
            jsonFile.close()
        }
    }
}
