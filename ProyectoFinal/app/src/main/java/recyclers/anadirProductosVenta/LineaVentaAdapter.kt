package recyclers.anadirProductosVenta

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.Albaran
import clases.Albaran_Producto
import clases.Producto
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LineaVentaAdapter(val actividadMadre: Activity, val datos: ArrayList<Producto>) :
    RecyclerView.Adapter<LineaVentaViewHolder>() {
    private lateinit var db: AppDataBase


    private val escrito = arrayOfNulls<String>(datos.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineaVentaViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return LineaVentaViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_anadir__venta,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: LineaVentaViewHolder, position: Int) {
        val producto: Producto = datos.get(position)
        holder.bindProducto(datos[position])
        holder.botonBorrar.setOnClickListener {


            datos.removeAt(position)
            this.notifyItemRemoved(position)
            notifyItemRangeChanged(position, datos.size)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun getEscrito(): Array<String?> {
        return escrito
    }




}