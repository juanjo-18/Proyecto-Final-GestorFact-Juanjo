package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

/**
 * Esta es la clase producto donde se encuentran los datos que lo componen en la base de datos
 *@author Juanjo medina
 */
@Parcelize
@Entity
data class Producto(
    /**
     * Esta es la referencia que se añadira automaticamente y es la clave primaria
     */
    @PrimaryKey(autoGenerate = true)@ColumnInfo("id") var referencia: Int =0,
    /**
     * Este es el nombre del producto
     */
    @ColumnInfo("nombre") var nombre:String?,
    /**
     * Este es el precio del producto
     */
    @ColumnInfo("precio") var precio:Float=0f,
    /**
     * Esto es la cantidad del producto
     */
    @ColumnInfo("cantidad") var cantidad:Int=0
) : Parcelable


/**
 * Funcion que le pasamos nombre,precio,cantidad y nos devuelve un producto ya con la referencia automatica
 */
fun Producto():Producto{
    val producto = Producto(nombre = null, precio = 0f, cantidad = 0)
    return producto
}