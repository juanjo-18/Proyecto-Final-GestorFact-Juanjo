package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Albaran : Parcelable {
    lateinit var titulo:String
    var nombreCliente:String?
    var fecha:LocalDate?
    lateinit var productos: ArrayList<Producto>
    var estado:String?
    var precioTotal:Int=0


    constructor(parcel: Parcel) : this() {
        titulo = parcel.readString()!!
        nombreCliente=parcel.readString()
        precioTotal=parcel.readInt()
    }
    constructor(titulo:String,nombre:String,fecha:LocalDate,precio:Int) : this(){
        this.titulo=titulo
        this.nombreCliente=nombre
        this.fecha=fecha
        this.precioTotal=precio

    }


    constructor() {
        val random: Random = Random()
        val nombresPosibles=arrayOf<String>("Jose Luis","Alex","Leti","Marcos","Alexis","Juan","Antonio","Abel","Joaquin","Pablo","Cristian","Jose","Ignacio","Juanmi","Juanjo","Carlos","Raul","Jero","Miguel")
        this.titulo="V-"+random.nextInt(300)+"/2023"
        this.nombreCliente=nombresPosibles[random.nextInt(nombresPosibles.size)]
        this.fecha=LocalDate.of(1900,10,10)
        this.productos = ArrayList<Producto>()
        for (i in 5 downTo 1) {
            var producto: Producto = Producto()
            this.productos.add(producto)
        }
        val estadosPosibles= arrayOf("Cerrado","Pendiente")
        this.estado=estadosPosibles[random.nextInt(estadosPosibles.size)]
        this.precioTotal=random.nextInt(100)



    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(nombreCliente)
        parcel.writeString(fecha.toString())
        //parcel.writeArray(arrayOf(productos))
        parcel.writeString(estado)
        parcel.writeInt(precioTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Albaran> {
        override fun createFromParcel(parcel: Parcel): Albaran {
            return Albaran(parcel)
        }

        override fun newArray(size: Int): Array<Albaran?> {
            return arrayOfNulls(size)
        }
    }
}