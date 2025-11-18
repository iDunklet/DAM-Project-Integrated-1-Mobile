package com.example.myapplication

import UserGameData
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Date

class GameOverActivity : AppCompatActivity() {

    private lateinit var jugador: Jugador
    private lateinit var partida: UserGameData

    private lateinit var btnRePlay: Button

    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        jugador = (intent.getSerializableExtra("JUGADOR") as? Jugador)!!

        val partidaActual = jugador.partidas.lastOrNull()
        partida = partidaActual!!
        val tvJugador = findViewById<TextView>(R.id.labelJugador)
        val tvAciertos = findViewById<TextView>(R.id.labelAciertos)
        val tvErrores = findViewById<TextView>(R.id.labelErrores)
        val tvTiempo = findViewById<TextView>(R.id.labelTiempo)

        btnRePlay = findViewById(R.id.btnTryAgain)
        btnExit = findViewById(R.id.btnExit)

        tvJugador.text = jugador.nombre
        tvAciertos.text = partida.aciertos.toString()
        tvErrores.text = partida.errores.toString()

        val mins = partida.gameTime ?: 0
        val diffMillis = partida.fechaHoraFin!!.time - partida.fechaHoraInicio.time
        val secs = ((diffMillis / 1000) % 60).toInt()

        tvTiempo.text = "${mins}m ${secs}s"

        btnRePlay.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        btnExit.setOnClickListener {
            finishAffinity()
        }

    }
}
