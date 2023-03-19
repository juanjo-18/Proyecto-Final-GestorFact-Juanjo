package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * Esta es la clase Factura_producto donde se encuentran los datos que lo componen en la base de datos
 * Aqui es donde guardaran todos los productos de la factura y el titulo de la factura
 * @author Juanjo medina
 */
@Parcelize
@Entity
data class Factura_Producto(
    /**
     * La referencia es la clave primaria y autoincrematada automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia: Int=0,
    /**
     * Titulo de la factura
     */
    @ColumnInfo("tituloFactura") var tituloFactura: String,
    /**
     * Nombre del producto que se añade
     */
    @ColumnInfo("nombreProducto") var nombreProducto: String?,
    /**
     * Precio del producto
     */
    @ColumnInfo("precio") var precio: Float = 0f,
    /**
     * Cantidad del producto
     */
    @ColumnInfo("cantidad") var cantidad: Int = 0,
    /**
     * Total de el precio multiplicado por la cantidad
     */
    @ColumnInfo("total") var total: Float = 0f
) : Parcelable
