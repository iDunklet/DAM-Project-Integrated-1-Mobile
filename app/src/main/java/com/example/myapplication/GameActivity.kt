package com.example.myapplication

import Partida
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import android.annotation.SuppressLint

class GameActivity_old : AppCompatActivity() {

    private var jugadorActual: Jugador? = null
    private var partidaActual: Partida? = null
    private lateinit var gameQuestions: List<PreguntaJuego>
    private var currentQuestionIndex: Int = 0
    private var score: Int = 0
    private var isAnswered: Boolean = false

    private lateinit var labelTextoPregunta1: TextView
    private lateinit var labelNumRonda: TextView
    private lateinit var labelNumTotalRondas: TextView
    private lateinit var btnNextRound: Button
    private lateinit var allButtons: List<Button>

    private val COLOR_PALETTE = listOf(
        R.color.naranja, R.color.azulOscuro, R.color.amarillo, R.color.negro, R.color.rojo
                                      )
    private val DRAWABLE_SELECCION_CORRECTA = R.drawable.boton_verde
    private val DRAWABLE_SELECCION_INCORRECTA = R.drawable.boton_rojo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Protección alrededor de la carga del layout para capturar InflateException u otros errores
        try {
            enableEdgeToEdge()
            setContentView(R.layout.game_activity)
        } catch (e: Exception) {
            reportFatalError("Error inflating layout", e)
            return
        }

        // Cargar Jugador
        try {
            val receivedIntent = intent
            @Suppress("DEPRECATION", "UNCHECKED_CAST")
            val serializableObject = receivedIntent.getSerializableExtra("JUGADOR")
            jugadorActual = serializableObject as? Jugador
            @Suppress("DEPRECATION", "UNCHECKED_CAST")
            val serializableObject2 = receivedIntent.getSerializableExtra("PARTIDA")
            partidaActual = serializableObject2 as? Partida
        } catch (e: Exception) {
            reportFatalError("Error reading intent extras", e)
            return
        }


        // Initialize views dentro de try/catch para capturar findViewById nulls
        try {
            initializeViews()
        } catch (e: Exception) {
            reportFatalError("Error initializing views. ¿IDs en el layout coinciden?", e)
            return
        }

        // Si no hay jugador no seguimos
        if (jugadorActual == null) {
            Toast.makeText(this, "Error: Jugador no cargado.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Obtener preguntas; defensivamente comprobamos tamaño
        try {
            val numRondasSeleccionadas = partidaActual!!.rondas
            gameQuestions = getRandomQuestions(numRondasSeleccionadas)
            if (gameQuestions.isEmpty()) {
                Toast.makeText(this, "No hay preguntas disponibles.", Toast.LENGTH_LONG).show()
                finish(); return
            }
            labelNumTotalRondas.text = numRondasSeleccionadas.toString()
            loadFirstQuestion()
        } catch (e: Exception) {
            reportFatalError("Error preparando preguntas", e)
        }
    }

    private fun initializeViews() {
        labelNumRonda = findViewById(R.id.labelNumRonda)
        labelNumTotalRondas = findViewById(R.id.labelNumTotalRondas)
        findViewById<ImageButton>(R.id.IconBack).setOnClickListener { finish() }

        labelTextoPregunta1 = findViewById(R.id.labelTextoPregunta1)

        val btnBox1: Button = findViewById(R.id.btnBox1)
        val btnBox2: Button = findViewById(R.id.btnBox2)
        val btnBox3: Button = findViewById(R.id.btnBox3)
        val btnBox4: Button = findViewById(R.id.btnBox4)
        val btnBox5: Button = findViewById(R.id.btnBox5)
        allButtons = listOf(btnBox1, btnBox2, btnBox3, btnBox4, btnBox5)

        btnNextRound = findViewById(R.id.buttonRegisterAceptar)
        btnNextRound.visibility = View.INVISIBLE

        btnNextRound.setOnClickListener {
            if (currentQuestionIndex >= gameQuestions.size) {
                endGame()
            } else {
                loadNextQuestion()
            }
        }

        for (button in allButtons) {
            button.setOnClickListener {
                if (!isAnswered) {
                    checkAnswer(button)
                }
            }
        }
    }

    private fun loadFirstQuestion() {
        currentQuestionIndex = 0
        loadNextQuestion()
    }

    private fun padOptionsToFive(options: List<String>): List<String> {
        // Si vienen menos de 5, repetimos/llenamos con cadenas vacías para evitar zip truncado
        val out = options.toMutableList()
        while (out.size < 5) out.add("")
        return out.take(5)
    }

    private fun loadNextQuestion() {
        if (currentQuestionIndex >= gameQuestions.size) {
            endGame()
            return
        }

        isAnswered = false
        btnNextRound.visibility = View.INVISIBLE
        resetButtonColors()

        val question = gameQuestions[currentQuestionIndex]
        labelNumRonda.text = (currentQuestionIndex + 1).toString()
        labelTextoPregunta1.text = question.enunciado_es ?: ""

        val shuffledOptions = padOptionsToFive(question.opciones_en.shuffled())

        try {
            when (question.categoria?.uppercase() ?: "") {
                "NUMBER" -> {
                    allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                        button.text = when (optionText.uppercase()) {
                            "ONE" -> "1"; "TWO" -> "2"; "THREE" -> "3"; "FOUR" -> "4"
                            "FIVE" -> "5"; "SIX" -> "6"; "SEVEN" -> "7"; "EIGHT" -> "8"
                            "NINE" -> "9"; "TEN" -> "10"; "TWELVE" -> "12"; "ZERO" -> "0"
                            else -> optionText
                        }
                        button.setBackgroundColor(ContextCompat.getColor(this, R.color.negro))
                        button.isEnabled = true
                    }
                }
                "COLOR" -> {
                    allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                        button.text = ""
                        button.isEnabled = true
                        val colorRes = when (optionText.uppercase()) {
                            "RED" -> R.color.red; "BLUE" -> R.color.blue; "GREEN" -> R.color.green
                            "YELLOW" -> R.color.yellow; "BLACK" -> R.color.negro; "WHITE" -> R.color.blanco
                            "PURPLE" -> R.color.purple; "ORANGE" -> R.color.orange; "BROWN" -> R.color.brown
                            "GREY" -> R.color.gray; else -> R.color.gray
                        }
                        button.setBackgroundColor(ContextCompat.getColor(this, colorRes))
                    }
                }
                "SHAPE" -> {
                    allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                        button.text = ""
                        button.isEnabled = true
                        val shapeRes = when (optionText.uppercase()) {
                            "CIRCLE" -> R.drawable.shape_circle; "SQUARE" -> R.drawable.shape_square
                            "TRIANGLE" -> R.drawable.shape_triangle; "RECTANGLE" -> R.drawable.shape_rectangle
                            "OVAL" -> R.drawable.shape_oval; "STAR" -> R.drawable.shape_star
                            "HEXAGON" -> R.drawable.shape_hexagon; "OCTAGON" -> R.drawable.shape_octagon
                            "DIAMOND" -> R.drawable.shape_diamond; else -> R.drawable.shape_default
                        }
                        // Protegemos si el drawable no existe (evita Resources.NotFoundException)
                        try {
                            button.setBackgroundResource(shapeRes)
                        } catch (e: Exception) {
                            button.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
                        }
                    }
                }
                else -> {
                    allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                        button.text = optionText
                        button.isEnabled = true
                    }
                }
            }
        } catch (e: Exception) {
            reportFatalError("Error al cargar pregunta (posible recurso faltante)", e)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkAnswer(selectedButton: Button) {
        if (isAnswered) return

        isAnswered = true
        val currentQuestion = try { gameQuestions[currentQuestionIndex] } catch (e: Exception) {
            reportFatalError("Index fuera de rango en checkAnswer", e); return
        }
        val selectedAnswer = selectedButton.text.toString()
        val isCorrect = selectedAnswer == currentQuestion.respuesta_correcta_en

        allButtons.forEach { it.isEnabled = false }

        allButtons.filter { it != selectedButton }.forEach { button ->
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.grisOscuro))
            button.setTextColor(ContextCompat.getColor(this, R.color.blanco))
        }

        if (isCorrect) {
            score++
            try { selectedButton.setBackgroundResource(DRAWABLE_SELECCION_CORRECTA) } catch(_: Exception) {}
            selectedButton.setTextColor(ContextCompat.getColor(this, R.color.blanco))
        } else {
            try { selectedButton.setBackgroundResource(DRAWABLE_SELECCION_INCORRECTA) } catch(_: Exception) {}
            selectedButton.setTextColor(ContextCompat.getColor(this, R.color.blanco))

            val correctButton = allButtons.find { it.text.toString() == currentQuestion.respuesta_correcta_en }
            correctButton?.let {
                try { it.setBackgroundResource(DRAWABLE_SELECCION_CORRECTA) } catch(_: Exception) {}
                it.setTextColor(ContextCompat.getColor(this, R.color.blanco))
            }
        }

        currentQuestionIndex++
        btnNextRound.visibility = View.VISIBLE
        btnNextRound.text = if (currentQuestionIndex >= gameQuestions.size) "VER RESULTADOS" else "SIGUIENTE RONDA"
    }

    private fun resetButtonColors() {
        val shuffledColors = COLOR_PALETTE.shuffled()
        allButtons.forEachIndexed { index, button ->
            // Si la paleta no tiene suficientes elementos protegemos el acceso
            val colorRes = if (index < shuffledColors.size) shuffledColors[index] else R.color.grisOscuro
            button.setBackgroundColor(ContextCompat.getColor(this, colorRes))
            button.backgroundTintList = null
            button.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun endGame() {
        val totalRounds = gameQuestions.size
        Toast.makeText(this, "Juego Terminado! Puntuación: $score / $totalRounds", Toast.LENGTH_LONG).show()
        btnNextRound.text = "FINALIZAR"
        btnNextRound.setOnClickListener { finish() }
        labelTextoPregunta1.text = "¡Juego Terminado! Puntuación Final: $score / $totalRounds"
    }

    private fun reportFatalError(msg: String, e: Exception) {
        Log.e("GameActivity", msg, e)
        // mostrar traza corta en Toast para desarrollo
        Toast.makeText(this, "$msg: ${e.javaClass.simpleName}: ${e.message}", Toast.LENGTH_LONG).show()
        // opcional: imprimir stacktrace en log para copiar luego
        e.printStackTrace()
        // no seguimos con la Activity en estado inconsistente
        finish()
    }
}
