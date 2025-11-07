package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private var numeroRondas: Int = 0
    private var nivelDificultad: Int = 0

    // Constantes para identificar el tipo de variable que se está actualizando
    // Esto coincide con los valores 1 y 2 usados en MetodosUniversal
    private val TARGET_ROUNDS = 1
    private val TARGET_DIFFICULTY = 2


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

        // Inicialización de Vistas
        val newPlayerName = findViewById<EditText>(R.id.TextboxNombre)
        val newPlayerAge = findViewById<EditText>(R.id.TextboxEdad)
        val btnAceptar = findViewById<Button>(R.id.buttonRegisterAceptar)


        btnRondas5 = findViewById(R.id.buttonRondas5)
        btnRondas10 = findViewById(R.id.buttonRondas10)
        btnRondas15 = findViewById(R.id.buttonRondas15)
        botonesRondas = listOf(btnRondas5, btnRondas10, btnRondas15)


        btnNivel1 = findViewById(R.id.buttonNivel1)
        btnNivel2 = findViewById(R.id.buttonNivel2)
        botonesNivel = listOf(btnNivel1, btnNivel2)


        // Listeners RONDAS
        btnRondas5.setOnClickListener {
            handleRoundSelection(it as Button, 5)
        }
        btnRondas10.setOnClickListener {
            handleRoundSelection(it as Button, 10)
        }
        btnRondas15.setOnClickListener {
            handleRoundSelection(it as Button, 15)
        }

        // Listeners NIVEL
        btnNivel1.setOnClickListener {
            handleLevelSelection(it as Button, 1)
        }
        btnNivel2.setOnClickListener {
            handleLevelSelection(it as Button, 2)
        }


        btnAceptar.setOnClickListener {
            if (validateInputs(newPlayerName, newPlayerAge)) {
                // Asegúrate de que tienes una clase Jugador definida
                // val newPlayer = Jugador(
                //     nombre = newPlayerName.text.toString(),
                //     edad = newPlayerAge.text.toString().toInt(),
                //     rondas = numeroRondas,
                //     nivel = nivelDificultad
                // )

                val intent = Intent(this, GameActivity::class.java)
                // Pasa los datos a GameActivity si es necesario
                intent.putExtra("ROUNDS", numeroRondas)
                intent.putExtra("DIFFICULTY", nivelDificultad)
                startActivity(intent)

            }
        }
    }
    
    private fun handleRoundSelection(boton: Button, rondas: Int) {
        MetodosUniversal.setSelectionStyle(
            botonPresionado = boton,
            valor = rondas,
            botonesGrupo = botonesRondas,
            targetVariable = TARGET_ROUNDS, // 1
            onUpdateData = ::updateDataVariables // Referencia al método de actualización local
                                          )
    }

    private fun handleLevelSelection(boton: Button, nivel: Int) {
        MetodosUniversal.setSelectionStyle(
            botonPresionado = boton,
            valor = nivel,
            botonesGrupo = botonesNivel,
            targetVariable = TARGET_DIFFICULTY, // 2
            onUpdateData = ::updateDataVariables // Referencia al método de actualización local
                                          )
    }


    private fun updateDataVariables(target: Int, value: Int) {
        if (target == TARGET_ROUNDS) {
            numeroRondas = value
            Log.d("RegisterActivity", "Rondas seleccionadas: $numeroRondas")
        } else if (target == TARGET_DIFFICULTY) {
            nivelDificultad = value
            Log.d("RegisterActivity", "Dificultad seleccionada: $nivelDificultad")
        }
    }

    // Validación de Inputs (sin cambios, excepto que ya tiene las variables actualizadas)
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