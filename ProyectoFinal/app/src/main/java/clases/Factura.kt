package clases

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

import java.util.*
import kotlin.collections.ArrayList

/**
 * Esta es la clase factira donde se encuentran los datos que lo componen en la base de datos
 * Aqui es donde guardaran todos las facturas
 * @author Juanjo medina
 */
@Parcelize
@Entity
data class Factura(
    /**
     * referencia es la clave primaria que se incrementa automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    /**
     * Titulo principal que hace referencia a la factura
     */
    @ColumnInfo("titulo") var titulo: String,
    /**
     * Nombre del cliente destinatario de la factura
     */
    @ColumnInfo("nombreCliente") var nombreCliente: String?,
    /**
     * Fecha del dia que se hace la factura
     */
    @ColumnInfo("fecha") var fecha: LocalDate?,
    /**
     * Puede ser Abono o Factura
     */
    @ColumnInfo("tipoFactura") var tipoFactura: String?,
    /**
     * Se guardara el estado de la factura pendiente de cobro o cobrada
     */
    @ColumnInfo("cobrada") var cobrada: Boolean = false,
    /**
     * Precio total de la factura
     */
    @ColumnInfo("precioTotal") var precioTotal: Float = 0f,

) : Parcelable
