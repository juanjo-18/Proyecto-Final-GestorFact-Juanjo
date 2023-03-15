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
data class Albaran_Producto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    @ColumnInfo("tituloAlbaran") var tituloAlbaran: String?,
    @ColumnInfo("nombreProducto") var nombreProducto: String?,
    @ColumnInfo("precio") var precio: Float = 0f,
    @ColumnInfo("cantidad") var cantidad: Int = 0,
    @ColumnInfo("total") var total: Float = 0f
    ) : Parcelable


fun Albaran_Producto():Albaran_Producto{
    val albaran_producto = Albaran_Producto(tituloAlbaran = null, nombreProducto = null, precio = 0f, cantidad = 0, total = 0f)
    return albaran_producto
}
