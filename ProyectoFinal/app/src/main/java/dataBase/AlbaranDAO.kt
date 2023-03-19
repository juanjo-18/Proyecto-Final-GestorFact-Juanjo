package dataBase

import androidx.room.*
import clases.Albaran
import clases.Cliente

@Dao
interface AlbaranDAO {
    @Query("SELECT * FROM albaran")
    fun getAll():List<Albaran>

    @Insert
    fun insert(albaran: Albaran)

    @Delete
    fun delete(albaran: Albaran)

    @Update
    fun updateUsers(vararg albaran: Albaran)


    @Query("UPDATE albaran SET nombreCliente= :nombreCliente,fecha= :fecha,estado= :estado,precio= :precio where titulo= :titulo")
    fun updateAlbaran(titulo:String,nombreCliente:String,fecha:String,estado:String,precio:Float):Int


    @Query("SELECT * FROM albaran WHERE titulo LIKE :searchText")
    fun buscarAlbaranPorTitulo(searchText: String): List<Albaran>
}