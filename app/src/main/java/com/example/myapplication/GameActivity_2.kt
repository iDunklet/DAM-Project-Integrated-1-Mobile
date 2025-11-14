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
class GameActivity_2 : AppCompatActivity() {

    private lateinit var labelTextoPregunta: TextView
    private lateinit var labelNumRonda: TextView
    private lateinit var labelNumTotalRondas: TextView
    private lateinit var btnNextRound: Button
    private lateinit var allButtons: List<Button>

    private lateinit var gameMechanics: GameMechanics
    private lateinit var gameQuestions: List<PreguntaJuego>
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)


        // Inicializar views
        labelTextoPregunta = findViewById(R.id.labelTextoPregunta1)
        labelNumRonda = findViewById(R.id.labelNumRonda)
        labelNumTotalRondas = findViewById(R.id.labelNumTotalRondas)
        btnNextRound = findViewById(R.id.buttonRegisterAceptar)

        val btnBox1: Button = findViewById(R.id.btnBox1)
        val btnBox2: Button = findViewById(R.id.btnBox2)
        val btnBox3: Button = findViewById(R.id.btnBox3)
        val btnBox4: Button = findViewById(R.id.btnBox4)
        val btnBox5: Button = findViewById(R.id.btnBox5)
        allButtons = listOf(btnBox1, btnBox2, btnBox3, btnBox4, btnBox5)

        val btnBack: ImageButton = findViewById(R.id.IconBack)
        btnBack.setOnClickListener { finish() }



        gameMechanics = GameMechanics(this)

        val allQuestions = PreguntaJuego.loadQuestionsFromJson(this, "nivel1.json")

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        val jugador = intent.getSerializableExtra("JUGADOR") as? Partida
        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        val partida = intent.getSerializableExtra("PARTIDA") as? Partida
        val totalRondas = partida?.rondas ?: allQuestions.size

        gameQuestions = allQuestions.shuffled().take(totalRondas)
        labelNumTotalRondas.text = totalRondas.toString()

        // Configurar clics en botones
        allButtons.forEach { button ->
            button.setOnClickListener {
                if (!isAnswered) onAnswerSelected(it as Button)
            }
        }

        btnNextRound.setOnClickListener {
            if (currentQuestionIndex >= gameQuestions.size) endGame()
            else loadQuestion()
        }

        // Cargar primera pregunta
        loadQuestion()
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= gameQuestions.size) {
            endGame()
            return
        }

        val question = gameQuestions[currentQuestionIndex]
        labelNumRonda.text = (currentQuestionIndex + 1).toString()
        labelTextoPregunta.text = question.enunciado_es

        gameMechanics.setupQuestion(allButtons, question)

        isAnswered = false
        btnNextRound.visibility = View.INVISIBLE
    }

    private fun onAnswerSelected(selectedButton: Button) {
        val question = gameQuestions[currentQuestionIndex]
        isAnswered = true

        val correct = gameMechanics.checkAnswer(selectedButton, allButtons, question)
        if (correct) score++

        currentQuestionIndex++
        btnNextRound.visibility = View.VISIBLE
        btnNextRound.text =
            if (currentQuestionIndex >= gameQuestions.size) "VER RESULTADOS"
            else "SIGUIENTE RONDA"
    }
    private fun endGame() {
        Toast.makeText(
            this,
            "Juego terminado: $score / ${gameQuestions.size}",
            Toast.LENGTH_LONG
                      ).show()
        labelTextoPregunta.text = "¡Puntuación final: $score / ${gameQuestions.size}!"
        btnNextRound.text = "FINALIZAR"
        btnNextRound.setOnClickListener { finish() }
    }
}
