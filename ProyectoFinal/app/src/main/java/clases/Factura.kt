package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

import java.util.*
import kotlin.collections.ArrayList

@Parcelize
@Entity
data class Factura(
    var titulo: String,
    var nombreCliente: String?,
    var fecha: LocalDate?,
    var productos: ArrayList<Producto>,
    var tipoFactura: String?,
    var cobrada: Boolean = false,
    var precioTotal: Int = 0
) : Parcelable

@RequiresApi(Build.VERSION_CODES.O)
fun Factura.random(): Factura {
    val random: Random = Random()
    val nombresPosibles = arrayOf<String>(
        "Jose Luis",
        "Alex",
        "Leti",
        "Marcos",
        "Alexis",
        "Juan",
        "Antonio",
        "Abel",
        "Joaquin",
        "Pablo",
        "Cristian",
        "Jose",
        "Ignacio",
        "Juanmi",
        "Juanjo",
        "Carlos",
        "Raul",
        "Jero",
        "Miguel"
    )
    val letrasPosibles = arrayOf("A", "F")
    this.titulo =
        letrasPosibles[random.nextInt(letrasPosibles.size)] + "-" + random.nextInt(300) + "/2023"
    this.nombreCliente = nombresPosibles[random.nextInt(nombresPosibles.size)]
    this.fecha = LocalDate.of(1900, 10, 10)
    this.productos = ArrayList<Producto>()
    val tiposPosibles = arrayOf("Abono", "Factura")
    this.tipoFactura = tiposPosibles[random.nextInt(tiposPosibles.size)]
    this.precioTotal = random.nextInt(100)
    this.cobrada = random.nextBoolean()
    return Factura(titulo, nombreCliente, fecha, productos, tipoFactura, cobrada, precioTotal)
}

