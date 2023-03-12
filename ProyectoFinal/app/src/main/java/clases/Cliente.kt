package clases

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.protobuf.DescriptorProtos.GeneratedCodeInfo
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity
class Cliente(
    @PrimaryKey(autoGenerate = true)@ColumnInfo("id") var referencia:Int=0,
    @ColumnInfo("nombre") var nombre:String?,
    @ColumnInfo("email") var email:String?,
    @ColumnInfo("nif") var nif:String?,
    @ColumnInfo("telefono") var telefono:Int=0,
    @ColumnInfo("direccion") var direccion:String?,
    @ColumnInfo("localidad") var localidad:String?,
    @ColumnInfo("provincia") var provincia:String?,
    @ColumnInfo("codigoPostal") var codigoPostal:Int=0
): Parcelable






