package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {

    // --- Estado del Juego ---
    private var jugadorActual: Jugador? = null
    private lateinit var gameQuestions: List<PreguntaJuego>
    private var currentQuestionIndex: Int = 0
    private var score: Int = 0
    private var isAnswered: Boolean = false

    // --- Vistas ---
    private lateinit var labelTextoPregunta1: TextView
    private lateinit var labelTextoPregunta2: TextView
    private lateinit var labelNumRonda: TextView
    private lateinit var labelNumTotalRondas: TextView
    private lateinit var btnNextRound: Button
    private lateinit var allButtons: List<Button>

    private val COLOR_PALETTE = listOf(
        R.color.naranja,
        R.color.azulOscuro,
        R.color.amarillo,
        R.color.black,
        R.color.rojo
                                      )
    private val DRAWABLE_SELECCION_CORRECTA = R.drawable.boton_verde
    private val COLOR_SELECCION_INCORRECTA = R.drawable.boton_rojo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.game_activity)

        // Cargar Jugador
        val receivedIntent = intent
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        val serializableObject = receivedIntent.getSerializableExtra("JUGADOR")
        jugadorActual = serializableObject as? Jugador

        initializeViews()

        if (jugadorActual != null) {
            val numRondasSeleccionadas = jugadorActual!!.rondas

            // Cargar preguntas y actualizar contador total
            gameQuestions = getRandomQuestions(numRondasSeleccionadas)
            labelNumTotalRondas.text = numRondasSeleccionadas.toString()

            loadFirstQuestion()
        } else {
            Toast.makeText(this, "Error de datos: Jugador no cargado.", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeViews() {
        // Inicialización de Vistas de Encabezado y Navegación
        labelNumRonda = findViewById(R.id.labelNumRonda)
        labelNumTotalRondas = findViewById(R.id.labelNumTotalRondas)
        findViewById<ImageButton>(R.id.IconBack).setOnClickListener { finish() }

        // Inicialización de Vistas de Pregunta
        labelTextoPregunta1 = findViewById(R.id.labelTextoPregunta1)
        labelTextoPregunta2 = findViewById(R.id.labelTextoPregunta2)

        // Inicialización de Botones de Opción
        val btnBox1: Button = findViewById(R.id.btnBox1)
        val btnBox2: Button = findViewById(R.id.btnBox2)
        val btnBox3: Button = findViewById(R.id.btnBox3)
        val btnBox4: Button = findViewById(R.id.btnBox4)
        val btnBox5: Button = findViewById(R.id.btnBox5)
        allButtons = listOf(btnBox1, btnBox2, btnBox3, btnBox4, btnBox5)

        // Inicialización de Botón de Siguiente Ronda
        btnNextRound = findViewById(R.id.buttonRegisterAceptar)
        btnNextRound.visibility = View.INVISIBLE

        btnNextRound.setOnClickListener {
            if (currentQuestionIndex >= gameQuestions.size) {
                endGame()
            } else {
                loadNextQuestion()
            }
            // Configuración de Listeners para los botones de opción
            for (button in allButtons) {
                button.setOnClickListener {
                    if (!isAnswered) {
                        checkAnswer(button)
                    }
                }
            }
        }


    }

    private fun loadFirstQuestion() {
        currentQuestionIndex = 0
        loadNextQuestion()
    }


    private fun loadNextQuestion() {
        if (currentQuestionIndex >= gameQuestions.size) {
            endGame()
            return
        }

        // Reset de estado y UI
        isAnswered = false
        btnNextRound.visibility = View.INVISIBLE
        resetButtonColors()

        val question = gameQuestions[currentQuestionIndex]

        // Actualizar UI: Contadores y Enunciados
        labelNumRonda.text = (currentQuestionIndex + 1).toString()
        labelTextoPregunta1.text = question.enunciado_es
        labelTextoPregunta2.text = ""

        // Asignar Opciones a Botones
        val shuffledOptions = question.opciones_en.shuffled()
        allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
            button.text = optionText
            button.isEnabled = true
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkAnswer(selectedButton: Button) {
        if (isAnswered) return

        isAnswered = true
        val currentQuestion = gameQuestions[currentQuestionIndex]
        val selectedAnswer = selectedButton.text.toString()
        val isCorrect = selectedAnswer == currentQuestion.respuesta_correcta_en

        allButtons.forEach { it.isEnabled = false }

        // LÓGICA DE COLOR CON DRAWABLES Y COLORES FIJOS
        if (isCorrect) {
            score++
            selectedButton.setBackgroundResource(DRAWABLE_SELECCION_CORRECTA)
        } else {
            selectedButton.setBackgroundColor(COLOR_SELECCION_INCORRECTA)

            // Resaltar la respuesta correcta con el drawable
            val correctAnswerButton = allButtons.find { it.text.toString() == currentQuestion.respuesta_correcta_en }
            correctAnswerButton?.setBackgroundResource(DRAWABLE_SELECCION_CORRECTA)
        }

        currentQuestionIndex++
        btnNextRound.visibility = View.VISIBLE
        btnNextRound.text = if (currentQuestionIndex >= gameQuestions.size) "VER RESULTADOS" else "SIGUIENTE RONDA"
    }

    private fun resetButtonColors() {

        val shuffledColors = COLOR_PALETTE.shuffled()
        allButtons.forEachIndexed { index, button ->
            button.setBackgroundColor(ContextCompat.getColor(this, shuffledColors[index]))
            button.backgroundTintList = null
        }
    }

    private fun endGame() {
        val totalRounds = gameQuestions.size
        Toast.makeText(this, "Juego Terminado! Puntuación: $score / $totalRounds", Toast.LENGTH_LONG).show()
        btnNextRound.text = "FINALIZAR"
        btnNextRound.setOnClickListener { finish() }

        labelTextoPregunta1.text = "¡Juego Terminado!"
        labelTextoPregunta2.text = "Puntuación Final: $score / $totalRounds"
    }
}