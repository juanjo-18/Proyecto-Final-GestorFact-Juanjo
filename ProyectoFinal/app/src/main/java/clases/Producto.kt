package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

@Parcelize
@Entity
data class Producto(
    @PrimaryKey(autoGenerate = true)@ColumnInfo("id") var referencia: Int =0,
    @ColumnInfo("nombre") var nombre:String?,
    @ColumnInfo("precio") var precio:Int=0,
    @ColumnInfo("cantidad") var cantidad:Int=0
) : Parcelable





fun Producto():Producto{

    val producto = Producto(nombre = null, precio = 0, cantidad = 0)
    return producto
}