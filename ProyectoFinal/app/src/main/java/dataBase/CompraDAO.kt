package dataBase

import androidx.room.*
import clases.Compra
import clases.Factura
import java.time.LocalDate

@Dao
interface CompraDAO {
    /**
     * Devuelve un array de todos las compra de la base de datos
     */
    @Query("SELECT * FROM compra")
    fun getAll():List<Compra>

    /**
     * Inserta un objeto compra en la base de datos
     */
    @Insert
    fun insert(compra: Compra)

    /**
     * Borra un objeto comrpa de la base de datos
     */
    @Delete
    fun delete(compra: Compra)

    /**
     * Actualiza un objeto compra de la base de datos
     */
    @Update
    fun updateUsers(vararg compra: Compra)

    /**
     * Actualiza un objeto compra pasandole todos los datos por parametros
     */
    @Query("UPDATE compra SET titulo=:tituloNuevo, nombreProveedor=:nombreProveedor, fecha=:fecha, tipoCompra=:tipoCompra, pagada=:pagada, precioTotal=:precio WHERE titulo=:tituloAntiguo")
    fun updateCompra(tituloNuevo:String, tituloAntiguo:String, nombreProveedor:String, fecha: LocalDate, tipoCompra:String, pagada:Boolean, precio:Float):Int

    /**
     * Busca una compra por el titulo que se le pasa por parametros devuelve una lista de la compra
     */
    @Query("SELECT * FROM compra WHERE titulo LIKE :searchText")
    fun buscarCompraPorTitulo(searchText: String): List<Compra>
}