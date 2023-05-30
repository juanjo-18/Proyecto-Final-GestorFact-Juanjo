package dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import clases.*

/**
 *Esta clase es la que se encarga de gestionar la base de datos de la aplicacion hay que añadirle las clases que
 * utilizan la base de datos y dentro se añade las interfaces
 *
 * @author Juanjo medina
 */
@TypeConverters(LocalDateConverter::class, ListConverter::class)
@Database(entities = [Producto::class, Cliente::class, Albaran::class, Factura::class,Albaran_Producto::class,Factura_Producto::class,Compra::class], version = 6)
abstract class AppDataBase:RoomDatabase() {
    abstract fun productoDAO(): ProductoDAO

    abstract fun clienteDAO():ClienteDAO
    abstract fun albaranDAO():AlbaranDAO
    abstract fun facturaDAO():FacturaDAO
    abstract fun albaran_ProductoDAO():Albaran_ProductoDAO
    abstract fun factura_ProductoDAO():Factura_ProductoDAO
    abstract fun compraDAO():CompraDAO



    /**
     * Esto se encarga que una vez que haga una version nueva se actualize
     */
    companion object {
        val MIGRATION_1_2 = Migracion1_2
    }
}




