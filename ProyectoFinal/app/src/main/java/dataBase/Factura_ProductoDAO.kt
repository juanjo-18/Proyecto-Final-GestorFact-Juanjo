package dataBase

import androidx.room.*
import clases.Albaran
import clases.Factura_Producto

@Dao
interface Factura_ProductoDAO {
    @Query("SELECT * FROM factura_producto")
    fun getAll():List<Factura_Producto>

    @Insert
    fun insert(facturaProducto: Factura_Producto)

    @Delete
    fun delete(facturaProducto: Factura_Producto)

    @Update
    fun updateUsers(vararg facturaProducto: Factura_Producto)
}