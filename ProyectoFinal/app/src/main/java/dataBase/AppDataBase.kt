package dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import clases.Cliente
import clases.Producto

@Database(entities = [Producto::class, Cliente::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun productoDAO(): ProductoDAO

    abstract fun clienteDAO():ClienteDAO



}




