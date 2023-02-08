package clases

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Cliente: Parcelable {
    lateinit var referencia:String
    lateinit var nombre:String

    constructor(parcel: Parcel) : this() {
        referencia = parcel.readString()!!
        nombre=parcel.readString()!!
    }

    constructor(referencia: String, nombre: String):this() {
        this.referencia=referencia
        this.nombre=nombre
    }

    constructor(){
        val random: Random = Random()
        val nombresPosibles=arrayOf<String>("Jose Luis","Alex","Leti","Marcos","Alexis","Juan","Antonio","Abel","Joaquin","Pablo","Cristian","Jose","Ignacio","Juanmi","Juanjo","Carlos","Raul","Jero","Miguel")
        val apellidosPosibles=arrayOf<String>("Corral","López","García","Riquelme","Tizu","Carmona","Asencio","Cocargeanu","Moreno","Oña","Rosas","Aranda","Medina","Alpresa","Martín","Páramos")
        this.nombre=nombresPosibles[random.nextInt(nombresPosibles.size)]+apellidosPosibles[random.nextInt(apellidosPosibles.size)]+apellidosPosibles[random.nextInt(apellidosPosibles.size)]
        this.referencia="CLI"+random.nextInt(1000)
    }

    override fun toString(): String {
        return nombre + ""
    }

    fun getNumeroSerie(): String{
        return referencia +""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(referencia)
        parcel.writeString(nombre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }
}