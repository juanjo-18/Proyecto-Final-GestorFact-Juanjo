package dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import clases.Cliente
import clases.DatosUsuario

@Dao
interface DatosUsuarioDAO {
    /**
     * Devuelve un array de todos los datos del usuario de la base de datos
     */
    @Query("SELECT * FROM datosUsuario")
    fun getAll():DatosUsuario

    /**
     * Inserta un objeto datosUsuario  en la base de datos
     */
    @Insert
    fun insert(datosUsuario: DatosUsuario)

    /**
     * Borra un objeto datosUsuario de la base de datos
     */
    @Delete
    fun delete(datosUsuario: DatosUsuario)

    /**
     * Actualiza un objeto datosUsuario de la base de datos añadiendo por parametros todos sus campos
     */
    @Query("UPDATE datosUsuario SET nombre= :nombre,email= :email,numeroCuenta=:numeroCuenta, nif= :nif,telefono= :telefono,direccion= :direccion, localidad= :localidad, provincia= :provincia,codigoPostal= :codigoPostal where id= :referencia")
    fun updateDatosUsuario(referencia:Int,nombre:String,email:String,numeroCuenta:String,nif:String,telefono:Int,direccion:String,localidad:String,provincia:String,codigoPostal:Int):Int

    /**
     * Busca en la base de datos un datosUsuario que tenga el nombre que se le pasa por parametro devuelve un objeto datosUsuario
     */
    @Query("SELECT * FROM datosUsuario WHERE nombre LIKE :searchText")
    fun buscarDatosUsuarioPorNombre(searchText: String): Cliente

}