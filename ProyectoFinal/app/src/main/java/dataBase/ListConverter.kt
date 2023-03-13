package dataBase

import androidx.room.TypeConverter
import clases.Producto
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): ArrayList<Producto> {
        val listType = object : TypeToken<ArrayList<Producto>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Producto>): String {
        return gson.toJson(list)
    }
}