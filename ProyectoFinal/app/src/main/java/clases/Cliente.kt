package clases

import android.os.Parcel
import android.os.Parcelable
import com.google.protobuf.DescriptorProtos.GeneratedCodeInfo
import java.util.*

class Cliente: Parcelable {
    lateinit var referencia:String
    var nombre:String?
    var email:String?
    var nif:String?
    var telefono:Int=0
    var direccion:String?
    var localidad:String?
    var provincia:String?
    var codigoPostal:Int=0



    constructor(parcel: Parcel) : this() {
        referencia = parcel.readString()!!
        nombre=parcel.readString()
        email=parcel.readString()
        nif=parcel.readString()
        telefono=parcel.readInt()
        direccion=parcel.readString()
        localidad=parcel.readString()
        provincia=parcel.readString()
        codigoPostal=parcel.readInt()

    }

    constructor(referencia: String, nombre: String,email: String,nif: String,telefono: Int,direccion: String,
    localidad:String,provincia:String,codigoPostal:Int):this() {
        this.referencia=referencia
        this.nombre=nombre
        this.email=email
        this.nif=nif
        this.telefono=telefono
        this.direccion=direccion
        this.localidad=localidad
        this.provincia=provincia
        this.codigoPostal=codigoPostal
    }

    constructor(){
        val random: Random = Random()
        val nombresPosibles=arrayOf<String>("Jose Luis","Alex","Leti","Marcos","Alexis","Juan","Antonio","Abel","Joaquin","Pablo","Cristian","Jose","Ignacio","Juanmi","Juanjo","Carlos","Raul","Jero","Miguel")
        val apellidosPosibles=arrayOf<String>("Corral","López","García","Riquelme","Tizu","Carmona","Asencio","Cocargeanu","Moreno","Oña","Rosas","Aranda","Medina","Alpresa","Martín","Páramos")
        this.nombre=nombresPosibles[random.nextInt(nombresPosibles.size)]+" "+apellidosPosibles[random.nextInt(apellidosPosibles.size)]+" "+apellidosPosibles[random.nextInt(apellidosPosibles.size)]
        this.referencia="CLI"+random.nextInt(1000)
        this.email=this.nombre+"@gmail.com"
        this.nif="B"+random.nextInt(9000000)
        this.telefono=random.nextInt(999999999)
        val direccionPosibles= arrayOf<String>("Calle Caceres","Calle Coin","Calle Canarias","Calle Teruel","Calle Almeria","Calle Madrid","Calle Ceuta")
        this.direccion=direccionPosibles[random.nextInt(direccionPosibles.size)]
        val localidadesPosibles= arrayOf<String>("Cártama","Málaga","Coin","Pizarra","Aljaima")
        this.localidad=localidadesPosibles[random.nextInt(localidadesPosibles.size)]
        val provinciasPosibles= arrayOf<String>("Madrid","Málaga","Almeria","Huelva","Granada","Sevilla")
        this.provincia=provinciasPosibles[random.nextInt(provinciasPosibles.size)]
        this.codigoPostal=random.nextInt(99999)
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
        parcel.writeString(email)
        parcel.writeString(nif)
        parcel.writeInt(telefono)
        parcel.writeString(direccion)
        parcel.writeString(localidad)
        parcel.writeString(provincia)
        parcel.writeInt(codigoPostal)
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