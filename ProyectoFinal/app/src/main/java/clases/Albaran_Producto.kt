package clases

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
@Entity
data class Albaran_Producto(
    @PrimaryKey(autoGenerate = false) @ColumnInfo("tituloAlbaran") var tituloAlbaran: String,
    @ColumnInfo("nombreProducto") var nombreProducto: String?,
    @ColumnInfo("precio") var precio: Int = 0,
    @ColumnInfo("cantidad") var cantidad: Int = 0,
    @ColumnInfo("total") var total: Int = 0
    ) : Parcelable
