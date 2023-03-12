package dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import clases.Producto

@Dao
interface ProductoDAO {
    @Query("SELECT * FROM producto")
    fun getAll():List<Producto>

    @Insert
    fun insert(producto: Producto)

}