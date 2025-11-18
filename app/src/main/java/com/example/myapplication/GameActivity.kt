package com.example.myapplication

import UserGameData
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
import android.graphics.drawable.Drawable

class GameActivity : AppCompatActivity() {

    private var jugadorActual: Jugador? = null
    private var partidaActual: UserGameData? = null
    private lateinit var gameQuestions: List<PreguntaJuego>
    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false

    private lateinit var labelTextoPregunta1: TextView
    private lateinit var labelNumRonda: TextView
    private lateinit var labelNumTotalRondas: TextView
    private lateinit var btnNextRound: Button
    private lateinit var allButtons: List<Button>

    private val RANDOM_COLORS = listOf(
        R.color.naranja, R.color.azulOscuro, R.color.amarillo, R.color.verde, R.color.purple)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.game_activity)

        try {
            jugadorActual = intent.getSerializableExtra("JUGADOR") as? Jugador
            partidaActual = intent.getSerializableExtra("PARTIDA") as? UserGameData
        } catch (e: Exception) {
            reportFatalError("Error leyendo datos de partida", e)
            return
        }

        initializeViews()

        if (jugadorActual == null || partidaActual == null) {
            Toast.makeText(this, "Error: jugador o partida no cargados", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val numRondas = partidaActual!!.rondas
       // gameQuestions = getRandomQuestions(numRondas)

        if (gameQuestions.isEmpty()) {
            Toast.makeText(this, "No hay preguntas disponibles", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        labelNumTotalRondas.text = numRondas.toString()
        loadQuestion()
    }

    private fun initializeViews() {
        labelNumRonda = findViewById(R.id.labelNumRonda)
        labelNumTotalRondas = findViewById(R.id.labelNumTotalRondas)
        labelTextoPregunta1 = findViewById(R.id.labelTextoPregunta1)
        btnNextRound = findViewById(R.id.buttonRegisterAceptar)
        val btnBack: ImageButton = findViewById(R.id.IconBack)

        val btnBox1: Button = findViewById(R.id.btnBox1)
        val btnBox2: Button = findViewById(R.id.btnBox2)
        val btnBox3: Button = findViewById(R.id.btnBox3)
        val btnBox4: Button = findViewById(R.id.btnBox4)
        val btnBox5: Button = findViewById(R.id.btnBox5)

        allButtons = listOf(btnBox1, btnBox2, btnBox3, btnBox4, btnBox5)

        btnNextRound.visibility = View.INVISIBLE
        btnBack.setOnClickListener { finish() }

        btnNextRound.setOnClickListener {
            if (currentQuestionIndex >= gameQuestions.size) endGame()
            else loadQuestion()
        }

        allButtons.forEach { button ->
            button.setOnClickListener {
                if (!isAnswered) checkAnswer(button)
            }
        }
    }

    private fun padOptionsToFive(options: List<String>): List<String> {
        val out = options.toMutableList()
        while (out.size < 5) out.add("")
        return out.take(5)
    }

    private fun loadQuestion() {
        if (currentQuestionIndex >= gameQuestions.size) {
            endGame()
            return
        }

        val question = gameQuestions[currentQuestionIndex]
        labelNumRonda.text = (currentQuestionIndex + 1).toString()
        labelTextoPregunta1.text = question.enunciadoEs

        val shuffledOptions = padOptionsToFive(question.opcionesEn.shuffled())

        // Reiniciamos botones: quitar foreground (verde/rojo) y habilitar
        allButtons.forEach { button ->
            button.foreground = null
            button.isEnabled = true
        }

        // Guardamos la opción de cada botón en el tag
        allButtons.zip(shuffledOptions).forEach { (button, option) ->
            button.tag = option
        }

        when (question.categoria.uppercase()) {
            "NUMBER" -> {
                allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                    button.text = when (optionText.uppercase()) {
                        "ONE" -> "1"; "TWO" -> "2"; "THREE" -> "3"; "FOUR" -> "4"
                        "FIVE" -> "5"; "SIX" -> "6"; "SEVEN" -> "7"; "EIGHT" -> "8"
                        "NINE" -> "9"; "TEN" -> "10"; "TWELVE" -> "12"; "ZERO" -> "0"
                        else -> optionText
                    }
                    // Color de fondo aleatorio para NUMBER
                    val randomColor = listOf(R.color.naranja, R.color.azulOscuro, R.color.amarillo, R.color.negro, R.color.rojo)
                        .shuffled().first()
                    button.setBackgroundColor(ContextCompat.getColor(this, randomColor))
                    button.setTextColor(ContextCompat.getColor(this, R.color.black))
                }
            }
            "SHAPE" -> {
                allButtons.zip(shuffledOptions).forEach { (button, shapeText) ->
                    button.text = ""
                    val shapeRes = when (shapeText.uppercase()) {
                        "CIRCLE" -> R.drawable.shape_circle
                        "SQUARE" -> R.drawable.shape_square
                        "TRIANGLE" -> R.drawable.shape_triangle
                        "RECTANGLE" -> R.drawable.shape_rectangle
                        "OVAL" -> R.drawable.shape_oval
                        "STAR" -> R.drawable.shape_star
                        "HEXAGON" -> R.drawable.shape_hexagon
                        "OCTAGON" -> R.drawable.shape_octagon
                        "DIAMOND" -> R.drawable.shape_diamond
                        else -> R.drawable.shape_default
                    }
                    button.setBackgroundResource(shapeRes)
                }
            }
            "COLOR" -> {
                allButtons.zip(shuffledOptions).forEach { (button, colorText) ->
                    button.text = ""
                    val colorRes = when (colorText.uppercase()) {
                        "RED" -> R.color.red; "BLUE" -> R.color.blue; "GREEN" -> R.color.green
                        "YELLOW" -> R.color.yellow; "BLACK" -> R.color.negro; "WHITE" -> R.color.blanco
                        "PURPLE" -> R.color.purple; "ORANGE" -> R.color.orange; "BROWN" -> R.color.brown
                        "GREY" -> R.color.gray; else -> R.color.gray
                    }
                    button.setBackgroundColor(ContextCompat.getColor(this, colorRes))
                }
            }
            else -> {
                allButtons.zip(shuffledOptions).forEach { (button, optionText) ->
                    button.text = optionText
                    val randomColor = listOf(R.color.naranja, R.color.azulOscuro, R.color.amarillo, R.color.negro, R.color.rojo)
                        .shuffled().first()
                    button.setBackgroundColor(ContextCompat.getColor(this, randomColor))
                    button.setTextColor(ContextCompat.getColor(this, R.color.black))
                }
            }
        }

        isAnswered = false
        btnNextRound.visibility = View.INVISIBLE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkAnswer(selectedButton: Button) {
        if (isAnswered) return
        isAnswered = true

        val question = gameQuestions[currentQuestionIndex]
        val selectedTag = selectedButton.tag?.toString() ?: ""
        val correct = question.respuestaCorrectaEn

        val isCorrect = isAnswerCorrect(selectedTag, correct)
        allButtons.forEach { it.isEnabled = false }

        // Función para obtener drawable semitransparente
        fun getFeedbackDrawable(correct: Boolean, isColorCategory: Boolean): Drawable {
            val drawableRes = if (correct) R.drawable.boton_verde else R.drawable.boton_rojo
            val drawable = ContextCompat.getDrawable(this, drawableRes)!!
            if (isColorCategory) {
                // aplicamos alpha para no tapar el color
                drawable.alpha = 150 // 0..255
            }
            return drawable
        }

        val isColorCategory = question.categoria.uppercase() == "COLOR"

        if (isCorrect) {
            selectedButton.foreground = getFeedbackDrawable(true, isColorCategory)
            score++
        } else {
            selectedButton.foreground = getFeedbackDrawable(false, isColorCategory)

            // Marcar botón correcto
            val correctButton = allButtons.find {
                isAnswerCorrect(it.tag?.toString() ?: "", correct)
            }
            correctButton?.foreground = getFeedbackDrawable(true, isColorCategory)
        }

        currentQuestionIndex++
        btnNextRound.visibility = View.VISIBLE
        btnNextRound.text =
            if (currentQuestionIndex >= gameQuestions.size) "VER RESULTADOS" else "SIGUIENTE RONDA"
    }


    private fun resetButtons() {
        allButtons.forEach { button ->
            button.isEnabled = true
            button.backgroundTintList = null
        }
    }

    private fun isAnswerCorrect(selected: String, correct: String): Boolean {
        // Map para convertir números escritos a texto (para categoría NUMBER)
        val map = mapOf(
            "1" to "ONE", "2" to "TWO", "3" to "THREE", "4" to "FOUR", "5" to "FIVE",
            "6" to "SIX", "7" to "SEVEN", "8" to "EIGHT", "9" to "NINE",
            "10" to "TEN", "12" to "TWELVE", "0" to "ZERO"
                       )

        val normalizedSelected = map[selected.uppercase()] ?: selected.uppercase()
        val normalizedCorrect = correct.uppercase()

        return normalizedSelected == normalizedCorrect
    }

    private fun endGame() {
        val total = gameQuestions.size
        Toast.makeText(this, "Juego terminado: $score / $total", Toast.LENGTH_LONG).show()
        labelTextoPregunta1.text = "¡Puntuación final: $score / $total!"
        btnNextRound.text = "FINALIZAR"
        btnNextRound.setOnClickListener { finish() }
    }



    private fun reportFatalError(msg: String, e: Exception) {
        Log.e("GameActivity", msg, e)
        Toast.makeText(this, "$msg: ${e.message}", Toast.LENGTH_LONG).show()
        finish()
    }
}
