package dataBase

import androidx.room.*
import clases.Cliente

/**
 *Esta interfaz se utiliza par hacer las consultas y modificaciones a la base de datos
 * A la clase cliente
 * @author Juanjo medina
 */
@Dao
interface ClienteDAO {
    /**
     * Devuelve un array de todos los clientes de la base de datos
     */
    @Query("SELECT * FROM cliente")
    fun getAll():List<Cliente>

    /**
     * Inserta un objeto cliente en la base de datos
     */
    @Insert
    fun insert(cliente: Cliente)

    /**
     * Borra un objeto cliente de la base de datos
     */
    @Delete
    fun delete(cliente: Cliente)

    /**
     * Actualiza un objeto cliente de la base de datos añadiendo por parametros todos sus campos
     */
    @Query("UPDATE cliente SET nombre= :nombre,email= :email, nif= :nif,telefono= :telefono,direccion= :direccion, localidad= :localidad, provincia= :provincia,codigoPostal= :codigoPostal where id= :referencia")
    fun updateCliente(referencia:Int,nombre:String,email:String,nif:String,telefono:Int,direccion:String,localidad:String,provincia:String,codigoPostal:Int):Int

    /**
     * Busca en la base de datos un cliente que tenga el nombre que se le pasa por parametro devuelve un objeto cliente
     */
    @Query("SELECT * FROM cliente WHERE nombre LIKE :searchText")
    fun buscarClientePorNombre(searchText: String): Cliente
    /**
     * Busca en la base de datos todos los clientes que tengan el nombre que se le pasa por parametro devuelve una lista de cliente
     */
    @Query("SELECT * FROM cliente WHERE nombre LIKE :searchText")
    fun buscarClientesPorNombre(searchText: String): List<Cliente>

}