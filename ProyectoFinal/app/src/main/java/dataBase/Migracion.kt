package dataBase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *Esta clase se encarga de hacer modificacion a la base de datos si hemos cambiado la version por ejemplo si hemos
 * hecho algun cambio en alguna tabla o si hemos creado alguna nueva tabla se añade aqui y si cambia la version
 * @author Juanjo medina
 */
object Migracion1_2 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Cliente` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT, `email` TEXT, `nif` TEXT, `telefono` INTEGER NOT NULL, `direccion` TEXT, `localidad` TEXT, `provincia` TEXT, `codigoPostal` INTEGER NOT NULL)")
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Albaran` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titulo` TEXT, `nombre` TEXT, `fecha` TEXT, `productos` TEXT, `estado` TEXT, `precio` INTEGER)")
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Factura` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titulo` TEXT, `nombre` TEXT, `fecha` TEXT, `productos` TEXT, `estado` TEXT, `precio` INTEGER)")
    }
}