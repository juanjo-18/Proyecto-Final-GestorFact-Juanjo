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

@Parcelize
@Entity
data class Factura(
    @PrimaryKey(autoGenerate = false) @ColumnInfo("titulo") var titulo: String,
    @ColumnInfo("nombreCliente") var nombreCliente: String?,
    @ColumnInfo("fecha") var fecha: LocalDate?,
    @ColumnInfo("tipoFactura") var tipoFactura: String?,
    @ColumnInfo("cobrada") var cobrada: Boolean = false,
    @ColumnInfo("PrecioTotal") var precioTotal: Int = 0,
    @ColumnInfo("dni") var cliente: String
) : Parcelable
