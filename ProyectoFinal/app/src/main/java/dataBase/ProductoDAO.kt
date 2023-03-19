package dataBase

import androidx.room.*
import clases.Producto

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase producto
 * @author Juanjo medina
 */
@Dao
interface ProductoDAO {
    /**
     * Devuelve un array de todos los productos de la base de datos
     */
    @Query("SELECT * FROM producto")
    fun getAll():List<Producto>

    /**
     * Inserta un objeto producto en la base de datos
     */
    @Insert
    fun insert(producto: Producto)

    /**
     * Borra un objeto producto de la base de datos
     */
    @Delete
    fun delete(producto: Producto)

    /**
     * Actualiza un objeto producto de la base de datos
     */
    @Update
    fun updateUsers(vararg producto: Producto)

    /**
     * Actualiza un objeto productos de la base de datos pasandole por parametros todos sus datos
     */
    @Query("UPDATE producto SET nombre= :nombre,cantidad= :cantidad, precio= :precio where id= :referencia")
    fun actualizarProducto(referencia:Int,nombre:String,cantidad:Int,precio:Float):Int

    /**
     * Busca en la base de datos todos los productos que tengan como nombre el pasado por parametros devuelve una lista de productos
     */
    @Query("SELECT * FROM producto WHERE nombre LIKE :searchText")
    fun buscarProductosPorNombre(searchText: String): List<Producto>

}