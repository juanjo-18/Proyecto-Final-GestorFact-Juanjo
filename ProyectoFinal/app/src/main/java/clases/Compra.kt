package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity
data class Compra(
    /**
     * referencia es la clave primaria que se incrementa automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    /**
     * Titulo principal que hace referencia a la comrpa
     */
    @ColumnInfo("titulo") var titulo: String,
    /**
     * Nombre del proveedor de la compra
     */
    @ColumnInfo("nombreProveedor") var nombreProveedor: String?,
    /**
     * Fecha del dia que se hace la compra
     */
    @ColumnInfo("fecha") var fecha: LocalDate?,
    /**
     * Puede ser Devolucion o Compra
     */
    @ColumnInfo("tipoCompra") var tipoCompra: String?,
    /**
     * Se guardara el estado de la compra pendiente o pagada
     */
    @ColumnInfo("pagada") var pagada: Boolean = false,
    /**
     * Precio total de la compra
     */
    @ColumnInfo("precioTotal") var precioTotal: Float = 0f,
): Parcelable
