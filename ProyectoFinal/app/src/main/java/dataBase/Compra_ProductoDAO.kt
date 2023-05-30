package dataBase

import androidx.room.*
import clases.Compra_Producto
import clases.Factura_Producto

@Dao
interface Compra_ProductoDAO {
    /**
     * Devuelve un array de todos las comrpas_producto de la base de datos
     */
    @Query("SELECT * FROM compra_producto")
    fun getAll():List<Compra_Producto>

    /**
     * Inserta un objeto compra producto en la base de datos
     */
    @Insert
    fun insert(compraProducto: Compra_Producto)

    /**
     * Borra un objeto compra producto de la base de datos
     */
    @Delete
    fun delete(compraProducto: Compra_Producto)

    /**
     * Actualiza un objeto compra producto de la base de datos
     */
    @Update
    fun updateUsers(vararg compraProducto: Compra_Producto)

    /**
     * Busca una comrpa producto en la base de datos si el titulo compra es el que se le pasa por parametros
     * devuelve una lista de productos que coincidan
     */
    @Query("SELECT * FROM compra_producto WHERE tituloCompra LIKE :searchText")
    fun buscarCompraProductoPorTitulo(searchText: String): List<Compra_Producto>

    /**
     * Borra todos las compra productos que tengan el nombre titulo factura que se le pasa por parametros
     */
    @Query("DELETE FROM compra_producto WHERE tituloCompra LIKE :searchText")
    fun borrarTodosPorTitulo(searchText: String)
}