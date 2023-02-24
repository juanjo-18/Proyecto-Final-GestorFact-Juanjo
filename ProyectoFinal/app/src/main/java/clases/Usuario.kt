package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.proyectofinal.R
import emergentes.Alerta
import java.time.LocalDate
import java.util.*

class Usuario  : Parcelable {

    lateinit var contraseña:String
    lateinit var email:String
    var fechaNacimiento: LocalDate? = null

    constructor(parcel: Parcel) : this() {
        contraseña = parcel.readString()!!
        email = parcel.readString()!!

    }

    constructor(pass:String,email: String, fn: LocalDate) :
            this() {
        this.contraseña=pass
        this.email=email
        this.fechaNacimiento = fn

    }


    @RequiresApi(Build.VERSION_CODES.O)
    constructor(email:String, contraseña:String):this(){
        this.email=email
        this.contraseña=contraseña
        this.fechaNacimiento= LocalDate.of(1900,10,10)

    }

    constructor(){

    }

    override fun toString(): String {
        return email
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contraseña)
       parcel.writeString(email)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }



}