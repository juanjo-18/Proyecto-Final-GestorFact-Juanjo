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

    @Update
    fun updateUsers(vararg cliente: Cliente)

    @Query("SELECT * FROM cliente WHERE nombre LIKE :searchText")
    fun buscarProductosPorNombre(searchText: String): List<Cliente>
}