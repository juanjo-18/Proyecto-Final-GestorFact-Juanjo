package dataBase

import androidx.room.*
import clases.Albaran
import clases.Albaran_Producto
import clases.Cliente

@Dao
interface Albaran_ProductoDAO {
    @Query("SELECT * FROM albaran_producto")
    fun getAll():List<Albaran_Producto>

    @Insert
    fun insert(albaranProducto: Albaran_Producto)

    @Delete
    fun delete(albaranProducto: Albaran_Producto)

    @Update
    fun updateUsers(vararg albaranProducto: Albaran_Producto)

    @Query("SELECT * FROM albaran_producto WHERE tituloAlbaran LIKE :searchText")
    fun buscarAlbaranProductoPorTitulo(searchText: String): List<Albaran_Producto>
    @Query("DELETE FROM albaran_producto WHERE tituloAlbaran LIKE :searchText")
    fun borrarTodosPorTitulo(searchText: String)




}