package clases

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate
import java.util.*

class Producto: Parcelable {

    lateinit var referencia:String
    var nombre:String?
    var precio:Int=0
    var cantidad:Int=0

    constructor(parcel: Parcel) : this() {
        referencia = parcel.readString()!!
        nombre = parcel.readString()
        precio = parcel.readInt()
        cantidad =parcel.readInt()
    }

    constructor(referencia: String, nombre: String, precio: Int, cantidad:Int) :
            this() {
        this.referencia=referencia
        this.nombre=nombre
        this.cantidad=cantidad
        this.precio=precio
    }

    constructor(){
        val random: Random = Random()
        val nombresPosibles= arrayOf<String>("Martillo","Arandela","Destornillador","Sierra","Nevera","Estufa","Cama")
        this.nombre=nombresPosibles[random.nextInt(nombresPosibles.size)]
        this.referencia="PR"+random.nextInt(1000)
        this.cantidad=random.nextInt(100)
        this.precio=random.nextInt(100)


    }

     fun getName(): String {
        return nombre + ""
    }

    override fun toString(): String {
        return nombre+""
    }

     fun getNumeroSerie(): String{
        return referencia +""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(referencia)
        parcel.writeString(nombre)
        parcel.writeInt(cantidad)
        parcel.writeInt(precio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}