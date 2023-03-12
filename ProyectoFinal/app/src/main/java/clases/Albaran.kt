package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Albaran(
    var titulo: String,
    var nombreCliente: String?,
    var fecha: LocalDate?,
    var productos: ArrayList<Producto>,
    var estado: String?,
    var precioTotal: Int = 0
) : Parcelable

    @RequiresApi(Build.VERSION_CODES.O)
    fun Albaran.random():Albaran{
        val random: Random = Random()
        val nombresPosibles=arrayOf<String>("Jose Luis","Alex","Leti","Marcos","Alexis","Juan","Antonio","Abel","Joaquin","Pablo","Cristian","Jose","Ignacio","Juanmi","Juanjo","Carlos","Raul","Jero","Miguel")
        this.titulo="V-"+random.nextInt(300)+"/2023"
        this.nombreCliente=nombresPosibles[random.nextInt(nombresPosibles.size)]
        this.fecha=LocalDate.of(1900,10,10)
        this.productos = ArrayList<Producto>()
        val estadosPosibles= arrayOf("Cerrado","Pendiente")
        this.estado=estadosPosibles[random.nextInt(estadosPosibles.size)]
        this.precioTotal=random.nextInt(100)
        return Albaran(titulo, nombreCliente, fecha, productos, estado, precioTotal)
    }





