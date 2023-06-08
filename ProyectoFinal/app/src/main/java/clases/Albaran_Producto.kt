package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.*

/**
 * Esta es la clase albaran_producto donde se encuentran los datos que lo componen en la base de datos
 * Aqui es donde guardaran todos los productos de cada albaran el tituloAlbaran
 * @author Juanjo medina
 */
@Parcelize
@Entity
data class Albaran_Producto(
    /**
     * referencia es la clave primaria que se incrementa automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    /**
     * Titulo principal que hace referencia al albaran
     */
    @ColumnInfo("tituloAlbaran") var tituloAlbaran: String?,
    /**
     * Nombre del producto que se añade al albaran
     */
    @ColumnInfo("nombreProducto") var nombreProducto: String?,
    /**
     * Nombre del cliente que se vincula el albaran
     */
    @ColumnInfo("nombreCliente") var nombreCliente: String?,
    /***
     * Tipo de albaran que puede ser albaran,presupuesto o pedido.
     */
    @ColumnInfo("tipoAlbaran") var tipoAlbaran: String?,
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


/**
 * Esta funcion devuelve un objeto Albaran_Producto
 */
fun Albaran_Producto():Albaran_Producto{
    val albaran_producto = Albaran_Producto(tituloAlbaran = null, nombreProducto = null, nombreCliente = null, tipoAlbaran = null, precio = 0f, cantidad = 0, total = 0f)
    return albaran_producto
}
