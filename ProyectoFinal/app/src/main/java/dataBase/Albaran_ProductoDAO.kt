package dataBase

import androidx.room.*
import clases.Albaran
import clases.Albaran_Producto
import clases.Cliente

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase albaran producto que contiene los productos con su titulo del albaran
 * @author Juanjo medina
 */
@Dao
interface Albaran_ProductoDAO {
    /**
     * Devuelve un array de todos los albaranes_productos de la base de datos
     */
    @Query("SELECT * FROM albaran_producto")
    fun getAll():List<Albaran_Producto>

    /**
     * Inserta un objeto albaran producto en la base de datos
     */
    @Insert
    fun insert(albaranProducto: Albaran_Producto)

    /**
     * Borra un objeto albaran producto de la base de datos
     */
    @Delete
    fun delete(albaranProducto: Albaran_Producto)

    /**
     * Actualiza un objeto albaran producto de la base de datos
     */
    @Update
    fun updateUsers(vararg albaranProducto: Albaran_Producto)

    /**
     * Busca un albaran producto en la base de datos si el titulo albaran es el que se le pasa por parametros
     * devuelve una lista de alabaranes productos que coincidan
     */
    @Query("SELECT * FROM albaran_producto WHERE tituloAlbaran LIKE :searchText")
    fun buscarAlbaranProductoPorTitulo(searchText: String): List<Albaran_Producto>

    /**
     * Borra todos los albaranes productos que tengan el nombre titulo albaran que se le pasa por parametros
     */
    @Query("DELETE FROM albaran_producto WHERE tituloAlbaran LIKE :searchText")
    fun borrarTodosPorTitulo(searchText: String)




}