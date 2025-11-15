package com.example.myapplication

import Partida
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.util.TypedValueCompat.dpToPx

class GameActivity_2 : AppCompatActivity() {

    private val randomColors = listOf(
        R.color.naranja,
        R.color.azulOscuro,
        R.color.amarillo,
        R.color.marron,
        R.color.purple
                                     )
    private lateinit var labelTextoPregunta: TextView
    private lateinit var allContainers: List<FrameLayout>
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
        setContentView(R.layout.game_activity2)


        // Inicializar views
        labelTextoPregunta = findViewById(R.id.labelTextoPregunta1)
        labelNumRonda = findViewById(R.id.labelNumRonda)
        labelNumTotalRondas = findViewById(R.id.labelNumTotalRondas)
        btnNextRound = findViewById(R.id.buttonRegisterAceptar)

        val containerBtn1: FrameLayout = findViewById(R.id.containerBtn1)
        val containerBtn2: FrameLayout = findViewById(R.id.containerBtn2)
        val containerBtn3: FrameLayout = findViewById(R.id.containerBtn3)
        val containerBtn4: FrameLayout = findViewById(R.id.containerBtn4)
        val containerBtn5: FrameLayout = findViewById(R.id.containerBtn5)
        allContainers = listOf(containerBtn1, containerBtn2, containerBtn3, containerBtn4, containerBtn5)

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

        allButtons.forEach { button ->
            button.setOnClickListener {
                if (!isAnswered) onAnswerSelected(it as Button)
            }
        }

        btnNextRound.setOnClickListener {
            if (currentQuestionIndex >= gameQuestions.size) endGame()
            else loadQuestion()
        }

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

        // Resetear contenedores y botones
        allContainers.forEach { container ->
            container.background = ContextCompat.getDrawable(this, R.drawable.edit_text_radius)
        }
        allButtons.forEach { button ->
            button.isEnabled = true

            allButtons.forEach { button ->
                val params = button.layoutParams
                params.width = FrameLayout.LayoutParams.MATCH_PARENT
                params.height = dpToPx(250)   // volver al tamaño original
                button.layoutParams = params

                button.requestLayout()

                val shuffledColors = randomColors.shuffled()
                allButtons.forEachIndexed { index, button ->
                    val colorRes = shuffledColors[index]
                    button.setBackgroundColor(ContextCompat.getColor(this, colorRes))
                    button.isEnabled = true
                }

                gameMechanics.setupQuestion(allButtons, question)

                isAnswered = false
                btnNextRound.visibility = View.INVISIBLE
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun onAnswerSelected(selectedButton: Button) {
        val question = gameQuestions[currentQuestionIndex]
        isAnswered = true

        // Llama a checkAnswer pasando también el contexto
        val correct = gameMechanics.checkAnswer(
            selectedButton,
            allButtons,
            allContainers,
            question, this)

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
