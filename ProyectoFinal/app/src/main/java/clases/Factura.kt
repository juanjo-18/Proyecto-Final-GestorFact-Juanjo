package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate

import java.util.*
import kotlin.collections.ArrayList

class Factura : Parcelable {
    lateinit var titulo:String
    var nombreCliente:String?
    var fecha: LocalDate?
    lateinit var productos: ArrayList<Producto>
    var tipoFactura:String?
    var cobrada:Boolean=false
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
        val letrasPosibles= arrayOf("A","F")
        this.titulo=letrasPosibles[random.nextInt(letrasPosibles.size)]+"-"+random.nextInt(300)+"/2023"
        this.nombreCliente=nombresPosibles[random.nextInt(nombresPosibles.size)]
        this.fecha=LocalDate.of(1900,10,10)
        this.productos = ArrayList<Producto>()
        for (i in 5 downTo 1) {
            var producto: Producto = Producto()
            this.productos.add(producto)
        }
        val tiposPosibles= arrayOf("Abono","Factura")
        this.tipoFactura=tiposPosibles[random.nextInt(tiposPosibles.size)]
        this.precioTotal=random.nextInt(100)
        this.cobrada=random.nextBoolean()

    }



    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(titulo)
        parcel.writeString(nombreCliente)
        parcel.writeString(fecha.toString())
        //parcel.writeArray(arrayOf(productos))
        parcel.writeString(tipoFactura)
        parcel.writeBoolean(cobrada)
        parcel.writeInt(precioTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Factura> {
        override fun createFromParcel(parcel: Parcel): Factura {
            return Factura(parcel)
        }

        override fun newArray(size: Int): Array<Factura?> {
            return arrayOfNulls(size)
        }
    }

}