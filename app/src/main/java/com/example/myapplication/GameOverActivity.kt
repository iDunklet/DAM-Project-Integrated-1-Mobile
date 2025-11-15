package com.example.myapplication

import UserGameData
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class GameOverActivity : AppCompatActivity() {

    private lateinit var jugador: Jugador
    private lateinit var partida: UserGameData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        jugador = (intent.getSerializableExtra("JUGADOR") as? Jugador)!!
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        partida = (intent.getSerializableExtra("PARTIDA") as? UserGameData)!!

        /*
        val partidas = listOf(
            UserGameData(1, 1, 5, 1, 4, Date(1769820900000), Date(1769820960000)),
            UserGameData(2, 2, 10, 1, 8, Date(1769820900000), Date(1769820960000)),
            UserGameData(3, 3, 5, 2, 5, Date(1769820900000), Date(1769820960000))
        )
         */


        val tvJugador = findViewById<TextView>(R.id.labelJugador)
        val tvAciertos = findViewById<TextView>(R.id.labelAciertos)
        val tvErrores = findViewById<TextView>(R.id.labelErrores)
        val tvTiempo = findViewById<TextView>(R.id.labelTiempo)

        /*
        val partidaActual = partidas.find { it.idPartida == 2 }
        partidaActual?.let {
            val errores = it.rondas - it.aciertos
            val tiempoMs = it.fechaHoraFin?.time?.minus(it.fechaHoraInicio.time)
            val tiempoSegs = tiempoMs?.div(1000)

            // tvJugador.text = "${it.idJugador}"
            tvAciertos.text = "${it.aciertos}"
            tvErrores.text = errores.toString()
            tvTiempo.text = "$tiempoSegs segs"
        }

         */

        tvJugador.text = jugador.nombre
        tvAciertos.text = partida.aciertos.toString()
        tvErrores.text = partida.errores.toString()

        val mins = partida.gameTime ?: 0
        val diffMillis = partida.fechaHoraFin!!.time - partida.fechaHoraInicio.time
        val secs = ((diffMillis / 1000) % 60).toInt()

        tvTiempo.text = "${mins}m ${secs}s"


    }


}
