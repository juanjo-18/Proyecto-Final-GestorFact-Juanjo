package dataBase

import androidx.room.*
import clases.Cliente

@Dao
interface ClienteDAO {
    @Query("SELECT * FROM cliente")
    fun getAll():List<Cliente>

    @Insert
    fun insert(cliente: Cliente)

    @Delete
    fun delete(cliente: Cliente)

    @Query("UPDATE cliente SET nombre= :nombre,email= :email, nif= :nif,telefono= :telefono,direccion= :direccion, localidad= :localidad, provincia= :provincia,codigoPostal= :codigoPostal where id= :referencia")
    fun updateCliente(referencia:Int,nombre:String,email:String,nif:String,telefono:Int,direccion:String,localidad:String,provincia:String,codigoPostal:Int):Int


    @Query("SELECT * FROM cliente WHERE nombre LIKE :searchText")
    fun buscarClientePorNombre(searchText: String): Cliente
    @Query("SELECT * FROM cliente WHERE nombre LIKE :searchText")
    fun buscarClientesPorNombre(searchText: String): List<Cliente>

}