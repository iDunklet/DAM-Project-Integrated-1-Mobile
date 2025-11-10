import java.util.Date

class Partida(
    val idPartida: Int,
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
}
