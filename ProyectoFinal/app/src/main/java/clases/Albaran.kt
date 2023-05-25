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
 * Esta es la clase albaran donde se encuentran los datos que lo componen en la base de datos
 * @author Juanjo medina
 */
@Parcelize
@Entity
data class Albaran(
    /**
     * referencia es la clave primaria que se incrementa automaticamente
     */
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") var referencia:Int=0,
    /**
     * Titulo principal que hace referencia al albaran
     */
    @ColumnInfo("titulo") var titulo: String,
    /**
     * Nombre del destinatario del albaran el cliente
     */
    @ColumnInfo("nombreCliente") var nombreCliente: String?,
    /**
     * Fecha de la creacion de la factura
     */
    @ColumnInfo("fecha") var fecha: LocalDate?,
    /**
     * Aqui es donde se guardara el estado del albaran que sera pendiente o Cerrado
     */
    @ColumnInfo("estado") var estado: String?,


    /**
     * Precio total del importe del albaran
     */
    @ColumnInfo("precio") var precioTotal: Float = 0f
    ) : Parcelable






