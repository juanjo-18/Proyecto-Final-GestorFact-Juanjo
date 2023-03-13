package dataBase

import androidx.room.*
import clases.Albaran
import clases.Factura

@Dao
interface FacturaDAO {
    @Query("SELECT * FROM factura")
    fun getAll():List<Factura>

    @Insert
    fun insert(factura: Factura)

    @Delete
    fun delete(factura: Factura)

    @Update
    fun updateUsers(vararg factura: Factura)
}