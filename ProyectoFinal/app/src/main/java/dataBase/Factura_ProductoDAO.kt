package dataBase

import androidx.room.*
import clases.Albaran
import clases.Albaran_Producto
import clases.Factura_Producto

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase albaran producto que contiene los productos con su titulo de la  factura
 * @author Juanjo medina
 */
@Dao
interface Factura_ProductoDAO {
    /**
     * Devuelve un array de todos las facturas_producto de la base de datos
     */
    @Query("SELECT * FROM factura_producto")
    fun getAll():List<Factura_Producto>

    /**
     * Inserta un objeto factura producto en la base de datos
     */
    @Insert
    fun insert(facturaProducto: Factura_Producto)

    /**
     * Borra un objeto factura producto de la base de datos
     */
    @Delete
    fun delete(facturaProducto: Factura_Producto)

    /**
     * Actualiza un objeto factura producto de la base de datos
     */
    @Update
    fun updateUsers(vararg facturaProducto: Factura_Producto)

    /**
     * Busca una factura producto en la base de datos si el titulo factura es el que se le pasa por parametros
     * devuelve una lista de alabaranes productos que coincidan
     */
    @Query("SELECT * FROM factura_producto WHERE tituloFactura LIKE :searchText")
    fun buscarFacturaProductoPorTitulo(searchText: String): List<Factura_Producto>

    /**
     * Borra todos las facturas productos que tengan el nombre titulo factura que se le pasa por parametros
     */
    @Query("DELETE FROM factura_producto WHERE tituloFactura LIKE :searchText")
    fun borrarTodosPorTitulo(searchText: String)
}