package dataBase

import androidx.room.*
import clases.Albaran
import clases.Factura
import java.time.LocalDate

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase Factura tendra todos sus datos correspondientes
 * @author Juanjo medina
 */
@Dao
interface FacturaDAO {
    /**
     * Devuelve un array de todos las facturas de la base de datos
     */
    @Query("SELECT * FROM factura")
    fun getAll():List<Factura>

    /**
     * Inserta un objeto factura en la base de datos
     */
    @Insert
    fun insert(factura: Factura)

    /**
     * Borra un objeto factura de la base de datos
     */
    @Delete
    fun delete(factura: Factura)

    /**
     * Actualiza un objeto factura de la base de datos
     */
    @Update
    fun updateUsers(vararg factura: Factura)

    /**
     * Actualiza un objeto factura pasandole todos los datos por parametros
     */
    @Query("UPDATE factura SET titulo=:tituloNuevo, nombreCliente=:nombreCliente, fecha=:fecha, tipoFactura=:tipoFactura, cobrada=:cobrada, precioTotal=:precio WHERE titulo=:tituloAntiguo")
    fun updateFactura(tituloNuevo:String,tituloAntiguo:String, nombreCliente:String, fecha: LocalDate, tipoFactura:String,cobrada:Boolean, precio:Float):Int

    /**
     * Busca una factura por el titulo que se le pasa por parametros devuelve una lista de la factura
     */
    @Query("SELECT * FROM factura WHERE titulo LIKE :searchText")
    fun buscarFacturaPorTitulo(searchText: String): List<Factura>
}