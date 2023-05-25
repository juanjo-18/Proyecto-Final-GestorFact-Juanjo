package dataBase

import androidx.room.*
import clases.Albaran
import clases.Cliente
import java.time.LocalDate

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase  albaran que contiene el objeto albaran
 * @author Juanjo medina
 */
@Dao
interface AlbaranDAO {
    /**
     * Devuelve un array de todos los albaranes de la base de datos
     */
    @Query("SELECT * FROM albaran")
    fun getAll():List<Albaran>

    /**
     * Inserta un objeto albaran en la base de datos
     */
    @Insert
    fun insert(albaran: Albaran)

    /**
     * Borra un objeto albaran de la base de datos
     */
    @Delete
    fun delete(albaran: Albaran)

    /**
     * Actualiza un objeto albaran de la base de datos
     */
    @Update
    fun updateUsers(vararg albaran: Albaran)


    /**
     * Actualiza un objeto albaran pasandole todos los datos por parametros
     */
    @Query("UPDATE albaran SET titulo= :tituloNuevo, nombreCliente= :nombreCliente,fecha= :fecha,estado= :estado,precio= :precio where titulo= :tituloAntiguo")
    fun updateAlbaran(tituloNuevo:String,tituloAntiguo:String, nombreCliente:String, fecha: LocalDate, estado:String, precio:Float):Int

    /**
     * Busca un albaran por el titulo que se le pasa por parametros devuelve una lista de albaran
     */
    @Query("SELECT * FROM albaran WHERE titulo LIKE :searchText")
    fun buscarAlbaranPorTitulo(searchText: String): List<Albaran>
}