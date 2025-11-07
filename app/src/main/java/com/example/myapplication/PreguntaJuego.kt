package com.example.myapplication


data class PreguntaJuego(
    val id: Int,
    val categoria: String,
    val enunciado_es: String,
    val respuesta_correcta_en: String,
    val opciones_en: List<String>
                        ) : java.io.Serializable



//Total de preguntas: 20
val gameDataset: List<PreguntaJuego> = listOf(
    // --- CATEGORÍA: COLOR (5 preguntas) ---
    PreguntaJuego(
        id = 1,
        categoria = "COLOR",
        enunciado_es = "Selecciona el color primario del cielo despejado.",
        respuesta_correcta_en = "BLUE",
        opciones_en = listOf("RED", "GREEN", "PURPLE", "YELLOW", "BLUE")
                 ),
    PreguntaJuego(
        id = 2,
        categoria = "COLOR",
        enunciado_es = "¿Qué color resulta de mezclar amarillo y azul?",
        respuesta_correcta_en = "GREEN",
        opciones_en = listOf("ORANGE", "PINK", "GREEN", "BROWN", "BLACK")
                 ),
    PreguntaJuego(
        id = 3,
        categoria = "COLOR",
        enunciado_es = "Encuentra el color de una fresa madura.",
        respuesta_correcta_en = "RED",
        opciones_en = listOf("WHITE", "RED", "GREY", "BROWN", "ORANGE")
                 ),
    PreguntaJuego(
        id = 10,
        categoria = "COLOR",
        enunciado_es = "Elige el color de la nieve fresca.",
        respuesta_correcta_en = "WHITE",
        opciones_en = listOf("BLACK", "RED", "GREY", "WHITE", "PURPLE")
                 ),
    PreguntaJuego(
        id = 11,
        categoria = "COLOR",
        enunciado_es = "Selecciona el color de un plátano maduro.",
        respuesta_correcta_en = "YELLOW",
        opciones_en = listOf("BLUE", "GREEN", "BROWN", "YELLOW", "RED")
                 ),

    // --- CATEGORÍA: NÚMERO (7 preguntas) ---
    PreguntaJuego(
        id = 4,
        categoria = "NUMBER",
        enunciado_es = "Selecciona el número que corresponde a ocho.",
        respuesta_correcta_en = "EIGHT",
        opciones_en = listOf("TEN", "THREE", "NINE", "EIGHT", "FIVE")
                 ),
    PreguntaJuego(
        id = 5,
        categoria = "NUMBER",
        enunciado_es = "¿Cuál es el doble de cinco?",
        respuesta_correcta_en = "TEN",
        opciones_en = listOf("FOUR", "EIGHT", "TEN", "ONE", "TWELVE")
                 ),
    PreguntaJuego(
        id = 6,
        categoria = "NUMBER",
        enunciado_es = "Elige el número entre uno y tres.",
        respuesta_correcta_en = "TWO",
        opciones_en = listOf("FOUR", "SIX", "TWO", "SEVEN", "FIVE")
                 ),
    PreguntaJuego(
        id = 12,
        categoria = "NUMBER",
        enunciado_es = "Selecciona el número de dedos en una mano.",
        respuesta_correcta_en = "FIVE",
        opciones_en = listOf("SIX", "FOUR", "THREE", "FIVE", "TEN")
                 ),
    PreguntaJuego(
        id = 13,
        categoria = "NUMBER",
        enunciado_es = "Encuentra el número que le sigue al seis.",
        respuesta_correcta_en = "SEVEN",
        opciones_en = listOf("EIGHT", "SEVEN", "FIVE", "NINE", "TEN")
                 ),
    PreguntaJuego(
        id = 14,
        categoria = "NUMBER",
        enunciado_es = "Elige el número par más pequeño.",
        respuesta_correcta_en = "TWO",
        opciones_en = listOf("ZERO", "ONE", "TWO", "THREE", "FOUR")
                 ),
    PreguntaJuego(
        id = 15,
        categoria = "NUMBER",
        enunciado_es = "¿Cuántas docenas hay en doce?",
        respuesta_correcta_en = "ONE",
        opciones_en = listOf("ONE", "TWO", "TWELVE", "TEN", "ZERO")
                 ),

    // --- CATEGORÍA: FORMA (SHAPE) (8 preguntas) ---
    PreguntaJuego(
        id = 7,
        categoria = "SHAPE",
        enunciado_es = "Selecciona la forma de una rueda de coche.",
        respuesta_correcta_en = "CIRCLE",
        opciones_en = listOf("SQUARE", "TRIANGLE", "OVAL", "RHOMBUS", "CIRCLE")
                 ),
    PreguntaJuego(
        id = 8,
        categoria = "SHAPE",
        enunciado_es = "¿Qué forma tiene la mayoría de los ladrillos?",
        respuesta_correcta_en = "RECTANGLE",
        opciones_en = listOf("STAR", "RECTANGLE", "CUBE", "TRAPEZOID", "PENTAGON")
                 ),
    PreguntaJuego(
        id = 9,
        categoria = "SHAPE",
        enunciado_es = "Elige la figura con cuatro lados iguales y cuatro ángulos rectos.",
        respuesta_correcta_en = "SQUARE",
        opciones_en = listOf("SQUARE", "TRIANGLE", "HEXAGON", "DIAMOND", "OVAL")
                 ),
    PreguntaJuego(
        id = 16,
        categoria = "SHAPE",
        enunciado_es = "Selecciona la figura geométrica con tres lados.",
        respuesta_correcta_en = "TRIANGLE",
        opciones_en = listOf("PENTAGON", "SQUARE", "HEXAGON", "TRIANGLE", "STAR")
                 ),
    PreguntaJuego(
        id = 17,
        categoria = "SHAPE",
        enunciado_es = "¿Qué forma tiene un huevo?",
        respuesta_correcta_en = "OVAL",
        opciones_en = listOf("RECTANGLE", "CIRCLE", "OVAL", "DIAMOND", "HEART")
                 ),
    PreguntaJuego(
        id = 18,
        categoria = "SHAPE",
        enunciado_es = "Figura con seis lados iguales.",
        respuesta_correcta_en = "HEXAGON",
        opciones_en = listOf("PENTAGON", "OCTAGON", "TRIANGLE", "HEXAGON", "SQUARE")
                 ),
    PreguntaJuego(
        id = 19,
        categoria = "SHAPE",
        enunciado_es = "Forma común para señales de alto (stop).",
        respuesta_correcta_en = "OCTAGON",
        opciones_en = listOf("SQUARE", "DIAMOND", "HEXAGON", "OCTAGON", "CIRCLE")
                 ),
    PreguntaJuego(
        id = 20,
        categoria = "SHAPE",
        enunciado_es = "Una forma con puntas usada para representar el sol.",
        respuesta_correcta_en = "STAR",
        opciones_en = listOf("OVAL", "STAR", "CIRCLE", "TRIANGLE", "RECTANGLE")
                 )
                                             )


fun getRandomQuestions(count: Int): List<PreguntaJuego> {
    require(count > 0 && count <= gameDataset.size) {
        "The number of questions requested ($count)"
    }
    return gameDataset.shuffled().take(count)
}