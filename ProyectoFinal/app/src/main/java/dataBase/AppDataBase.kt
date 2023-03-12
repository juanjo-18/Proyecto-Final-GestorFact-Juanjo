package dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import clases.Producto

@Database(entities = [Producto::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun productoDAO(): ProductoDAO



}