import java.io.Serializable
import java.util.Date

class UserGameData(
    var rondas: Int = 0,
    val dificultad: Int,
    var aciertos: Int = 0,
    var errores: Int = 0,
    val fechaHoraInicio: Date = Date(),
    var fechaHoraFin: Date? = null,
    var gameTime: Int? = 0
) : Serializable {
    fun registrarAcierto() {
        aciertos++
    }

    fun registrarError() {
        errores++
    }

    fun finalizarPartida() {
        fechaHoraFin = Date()
        gameTime = ((fechaHoraFin?.time?.minus(fechaHoraInicio.time))?.div(1000))?.toInt()
    }
}
