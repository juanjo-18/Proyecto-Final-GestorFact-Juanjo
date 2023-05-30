package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Compra_Producto(
    /**
     * referencia es la clave primaria que se incrementa automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    /**
     * Titulo principal que hace referencia a la comrpa
     */
    @ColumnInfo("tituloCompra") var tituloComrpa: String?,
    /**
     * Nombre del producto que se añade al compra
     */
    @ColumnInfo("nombreProducto") var nombreProducto: String?,

    /**
     * Precio que cuesta el producto
     */
    @ColumnInfo("precio") var precio: Float = 0f,
    /**
     * Cantidad del producto añadido
     */
    @ColumnInfo("cantidad") var cantidad: Int = 0,
    /**
     * Total del precio multiplicado por la cantidad
     */
    @ColumnInfo("total") var total: Float = 0f
) : Parcelable
