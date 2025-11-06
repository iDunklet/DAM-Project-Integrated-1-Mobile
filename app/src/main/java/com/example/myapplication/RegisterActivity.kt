package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity {

    class RegisterActivity : AppCompatActivity() {

        private var numeroRondas: Int = 0
        private var nivelDificultad: Int = 0


        private lateinit var btnRondas5: Button
        private lateinit var btnRondas10: Button
        private lateinit var btnRondas15: Button
        private lateinit var botonesRondas: List<Button>

        private lateinit var btnNivel1: Button
        private lateinit var btnNivel2: Button
        private lateinit var botonesNivel: List<Button>


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.register_activity)

            var newPlayerName = this.findViewById(R.id.TextboxNombre) as EditText
            var newPlayerAge = this.findViewById(R.id.TextboxEdad) as EditText
            val btnAceptar = findViewById(R.id.buttonRegisterAceptar) as Button


            btnRondas5 = findViewById(R.id.buttonRondas5)
            btnRondas10 = findViewById(R.id.buttonRondas10)
            btnRondas15 = findViewById(R.id.buttonRondas15)
            botonesRondas = listOf(btnRondas5, btnRondas10, btnRondas15)


            btnNivel1 = findViewById(R.id.buttonNivel1)
            btnNivel2 = findViewById(R.id.buttonNivel2)
            botonesNivel = listOf(btnNivel1, btnNivel2)


            //Listeners  RONDAS
            btnRondas5.setOnClickListener {
                handleRoundSelection(it as Button, 5)
            }
            btnRondas10.setOnClickListener {
                handleRoundSelection(it as Button, 10)
            }
            btnRondas15.setOnClickListener {
                handleRoundSelection(it as Button, 15)
            }

            //Listeners  NIVEL
            btnNivel1.setOnClickListener {
                handleLevelSelection(it as Button, 1)
            }
            btnNivel2.setOnClickListener {
                handleLevelSelection(it as Button, 2)
            }


            btnAceptar.setOnClickListener {
                if (validateInputs(newPlayerName, newPlayerAge)) {
                    val newPlayer = Jugador(
                        nombre = newPlayerName.text.toString(),
                        edad = newPlayerAge.text.toString().toInt(),
                        rondas = numeroRondas,
                        nivel = nivelDificultad
                                           )

                    // Mensaje de éxito y preparación para la siguiente actividad
                    Toast.makeText(this, "Jugador ${newPlayer.nombre} registrado. Rondas: ${newPlayer.rondas}", Toast.LENGTH_LONG).show()
                    Log.d("REGISTER", "Datos del Jugador listos: $newPlayer")

                    // Aquí podrías iniciar tu Activity de juego
                    // val intent = Intent(this, GameActivity::class.java)
                    // intent.putExtra("PLAYER_DATA", newPlayer)
                    // startActivity(intent)

        }
    }
}
        private fun setSelectionStyle(botonPresionado: Button, valor: Int, botonesGrupo: List<Button>, targetVariable: Int) {


            val drawableNormal = R.drawable.edit_text_radius
            val drawableSeleccionado = R.drawable.boton_verde



            for (boton in botonesGrupo) {
                boton.setBackgroundResource(drawableNormal)
            }


            botonPresionado.setBackgroundResource(drawableSeleccionado)



            if (targetVariable == 1) {
                numeroRondas = valor
            } else if (targetVariable == 2) {
                nivelDificultad = valor
            }
        }


        private fun handleRoundSelection(boton: Button, rondas: Int) {
            setSelectionStyle(boton, rondas, botonesRondas, targetVariable = 1)
        }

        private fun handleLevelSelection(boton: Button, nivel: Int) {
            setSelectionStyle(boton, nivel, botonesNivel, targetVariable = 2)
        }

        //prueva
        private fun validateInputs(nameEt: EditText, ageEt: EditText): Boolean {
            if (nameEt.text.isNullOrBlank()) {
                nameEt.error = "El nombre es obligatorio."
                Toast.makeText(this, "Falta el nombre.", Toast.LENGTH_SHORT).show()
                return false
            }
            if (ageEt.text.isNullOrBlank() || ageEt.text.toString().toIntOrNull() == null) {
                ageEt.error = "La edad debe ser un número."
                Toast.makeText(this, "Falta o es incorrecta la edad.", Toast.LENGTH_SHORT).show()
                return false
            }
            if (numeroRondas == 0) {
                Toast.makeText(this, "Debes seleccionar el número de rondas.", Toast.LENGTH_SHORT).show()
                return false
            }
            if (nivelDificultad == 0) {
                Toast.makeText(this, "Debes seleccionar la dificultad.", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }
}