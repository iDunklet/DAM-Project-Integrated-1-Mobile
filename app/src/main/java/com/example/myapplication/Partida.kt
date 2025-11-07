import java.util.Date

class Partida(
    val idJugador: Int,
    val rondas: Int,
    val dificultad: Int,
    var aciertos: Int = 0,
    val fechaHoraInicio: Date = Date(),
    var fechaHoraFin: Date? = null
) {
    fun registrarAcierto() {
        if (aciertos < rondas) aciertos++
    }

    fun finalizarPartida() {
        fechaHoraFin = Date()
    }

    fun obtenerResumen(): String {
        return """
            ID Jugador: $idJugador
            Dificultad: $dificultad
            Rondas totales: $rondas
            Aciertos: $aciertos
            Fecha inicio: $fechaHoraInicio
            Fecha fin: ${fechaHoraFin ?: "En curso"}
        """.trimIndent()
    }
}
