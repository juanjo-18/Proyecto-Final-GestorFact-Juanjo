package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Factura_Producto(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia: Int=0,
    @ColumnInfo("tituloFactura") var tituloFactura: String,
    @ColumnInfo("nombreProducto") var nombreProducto: String?,
    @ColumnInfo("precio") var precio: Float = 0f,
    @ColumnInfo("cantidad") var cantidad: Int = 0,
    @ColumnInfo("total") var total: Float = 0f
) : Parcelable
