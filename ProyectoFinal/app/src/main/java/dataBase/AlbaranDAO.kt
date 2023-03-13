package dataBase

import androidx.room.*
import clases.Albaran

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

}