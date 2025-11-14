package com.example.myapplication

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import android.graphics.drawable.Drawable

class GameMechanics(private val context: Context) {
    private val randomColors = listOf(
        R.color.naranja, R.color.azulOscuro, R.color.amarillo, R.color.verde, R.color.purple
                                     )

    /**
     * Configura los botones según la pregunta y la categoría.
     * Aplica colores aleatorios para NUMBER y SHAPE, y para COLOR deja fondo aleatorio.
     * Guarda el valor real de cada botón en tag.
     */
    fun setupQuestion(buttons: List<Button>, question: PreguntaJuego) {
        val shuffledOptions = question.opciones_en.shuffled()

        buttons.zip(shuffledOptions).forEach { (button, option) ->
            button.tag = option
            button.isEnabled = true
            button.foreground = null // quitar foreground anterior
        }

        when (question.categoria.uppercase()) {
            "NUMBER" -> {
                buttons.zip(shuffledOptions).forEach { (button, option) ->
                    button.text = mapNumber(option)
                    val color = randomColors.random()
                    button.setBackgroundColor(ContextCompat.getColor(context, color))
                }
            }
            "SHAPE" -> {
                buttons.zip(shuffledOptions).forEach { (button, option) ->
                    button.text = ""
                    val drawable = mapShape(option)
                    button.setBackgroundResource(drawable)
                }
            }
            "COLOR" -> {
                buttons.zip(shuffledOptions).forEach { (button, option) ->
                    button.text = ""
                    val color = mapColor(option)
                    button.setBackgroundColor(ContextCompat.getColor(context, color))
                }
            }
            else -> {
                buttons.zip(shuffledOptions).forEach { (button, option) ->
                    button.text = option
                    val color = randomColors.random()
                    button.setBackgroundColor(ContextCompat.getColor(context, color))
                }
            }
        }
    }

    /**
     * Comprueba la respuesta seleccionada y aplica los foregrounds correspondientes.
     * Devuelve true si la respuesta era correcta, false si no.
     */
    fun checkAnswer(selectedButton: Button, buttons: List<Button>, question: PreguntaJuego): Boolean {
        val selectedTag = selectedButton.tag?.toString() ?: ""
        val correctAnswer = question.respuesta_correcta_en

        val isCorrect = isAnswerCorrect(selectedTag, correctAnswer)

        // Deshabilitar todos los botones
        buttons.forEach { it.isEnabled = false }

            if (isCorrect) {
                when(question.categoria.uppercase()) {
                    "NUMBER" ->  selectedButton.background = getDrawable("boton_verde")
                    "SHAPE" -> var drawableId =
                    miIconoResultado.setBackgroundResource(R.drawable.borde_verde_si_correcto)

                }
                selectedButton.background = getDrawable("boton_verde")
            } else {
                selectedButton.background = getDrawable("boton_rojo")
                val correctButton = buttons.find {
                    isAnswerCorrect(it.tag?.toString() ?: "", correctAnswer)
                }
                correctButton?.background = getDrawable("boton_verde")
            }


        return isCorrect
    }

    /** Normaliza números escritos como texto para la categoría NUMBER */
    fun isAnswerCorrect(selected: String, correct: String): Boolean {
        val map = mapOf(
            "1" to "ONE", "2" to "TWO", "3" to "THREE", "4" to "FOUR", "5" to "FIVE",
            "6" to "SIX", "7" to "SEVEN", "8" to "EIGHT", "9" to "NINE",
            "10" to "TEN", "12" to "TWELVE", "0" to "ZERO"
                       )
        val normalizedSelected = map[selected.uppercase()] ?: selected.uppercase()
        return normalizedSelected == correct.uppercase()
    }

    /** Map número escrito a número visual */
    private fun mapNumber(option: String): String {
        return when (option.uppercase()) {
            "ONE" -> "1"; "TWO" -> "2"; "THREE" -> "3"; "FOUR" -> "4"
            "FIVE" -> "5"; "SIX" -> "6"; "SEVEN" -> "7"; "EIGHT" -> "8"
            "NINE" -> "9"; "TEN" -> "10"; "TWELVE" -> "12"; "ZERO" -> "0"
            else -> option
        }
    }

    /** Map string a drawable de forma */
    private fun mapShape(option: String): Int {
        return when (option.uppercase()) {
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
    }

    /** Map string a color de Android */
    private fun mapColor(option: String): Int {
        return when (option.uppercase()) {
            "RED" -> R.color.red
            "BLUE" -> R.color.blue
            "GREEN" -> R.color.green
            "YELLOW" -> R.color.yellow
            "BLACK" -> R.color.negro
            "WHITE" -> R.color.blanco
            "PURPLE" -> R.color.purple
            "ORANGE" -> R.color.orange
            "BROWN" -> R.color.brown
            "GREY" -> R.color.gray
            else -> R.color.gray
        }
    }

    /** Obtiene drawable por nombre */
    private fun getDrawable(name: String): Drawable? {
        val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
        return ContextCompat.getDrawable(context, resId)
    }

    companion object {
        public lateinit val drawableId;
    }
}
