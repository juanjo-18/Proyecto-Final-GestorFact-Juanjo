package dataBase

import androidx.room.*
import clases.Producto

@Dao
interface ProductoDAO {
    @Query("SELECT * FROM producto")
    fun getAll():List<Producto>

    @Insert
    fun insert(producto: Producto)

    @Delete
    fun delete(producto: Producto)

    @Update
    fun updateUsers(vararg producto: Producto)

    @Query("SELECT * FROM producto WHERE nombre LIKE :searchText")
    fun buscarProductosPorNombre(searchText: String): List<Producto>

}