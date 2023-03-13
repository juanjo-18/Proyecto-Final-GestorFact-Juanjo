package dataBase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migracion1_2 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Cliente` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT, `email` TEXT, `nif` TEXT, `telefono` INTEGER NOT NULL, `direccion` TEXT, `localidad` TEXT, `provincia` TEXT, `codigoPostal` INTEGER NOT NULL)")
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Albaran` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titulo` TEXT, `nombre` TEXT, `fecha` TEXT, `productos` TEXT, `estado` TEXT, `precio` INTEGER)")
        //database.execSQL("CREATE TABLE IF NOT EXISTS `Factura` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titulo` TEXT, `nombre` TEXT, `fecha` TEXT, `productos` TEXT, `estado` TEXT, `precio` INTEGER)")
    }
}