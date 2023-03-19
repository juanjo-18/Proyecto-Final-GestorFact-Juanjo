package clases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.protobuf.DescriptorProtos.GeneratedCodeInfo
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Esta es la clase cliente donde se encuentran los datos que lo componen en la base de datos
 * Aqui es donde guardaran todos los clientes con todos sus datos
 * @author Juanjo medina
 */
@Parcelize
@Entity
class Cliente(
    /**
     * La referencia se añade automaticamente y es la clave primaria
     */
    @PrimaryKey(autoGenerate = true)@ColumnInfo("id") var referencia:Int=0,
    /**
     * Nombre del cliente
     */
    @ColumnInfo("nombre") var nombre:String?,
    /**
     * Email del cliente
     */
    @ColumnInfo("email") var email:String?,
    /**
     * Dni o Nif del cliente
     */
    @ColumnInfo("nif") var nif:String?,
    /**
     * Numero de telefono del cliente
     */
    @ColumnInfo("telefono") var telefono:Int=0,
    /**
     * Direccion del cliente
     */
    @ColumnInfo("direccion") var direccion:String?,
    /**
     * Localidad del cliente
     */
    @ColumnInfo("localidad") var localidad:String?,
    /**
     * Provincia del cliente
     */
    @ColumnInfo("provincia") var provincia:String?,
    /**
     * Codigo postal del cliente
     */
    @ColumnInfo("codigoPostal") var codigoPostal:Int=0
): Parcelable

