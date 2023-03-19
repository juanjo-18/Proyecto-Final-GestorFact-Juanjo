package dataBase

import androidx.room.*
import clases.Albaran
import clases.Factura

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
}