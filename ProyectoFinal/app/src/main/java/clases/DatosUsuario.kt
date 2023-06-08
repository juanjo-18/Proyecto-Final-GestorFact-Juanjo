package clases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
/**
 * Esta es la clase datosUsuario donde se encuentran los datos que lo componen en la base de datos
 * Aqui es donde guardaran todos los datos del usuario que inicia sesion
 * @author Juanjo medina
 */
@Parcelize
@Entity
class DatosUsuario(
    /**
     * La referencia se añade automaticamente y es la clave primaria
     */
    @PrimaryKey(autoGenerate = true)@ColumnInfo("id") var referencia:Int=0,

    @ColumnInfo("nombre") var nombre:String?,

    @ColumnInfo("email") var email:String?,

    @ColumnInfo("numeroCuenta") var numeroCuenta:String?,

    @ColumnInfo("nif") var nif:String?,

    @ColumnInfo("telefono") var telefono:Int=0,

    @ColumnInfo("direccion") var direccion:String?,

    @ColumnInfo("localidad") var localidad:String?,

    @ColumnInfo("provincia") var provincia:String?,

    @ColumnInfo("codigoPostal") var codigoPostal:Int=0
): Parcelable

