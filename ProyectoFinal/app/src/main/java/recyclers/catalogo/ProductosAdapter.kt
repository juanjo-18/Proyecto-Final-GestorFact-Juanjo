package recyclers.catalogo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.Producto
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.stream.Collectors

class ProductosAdapter (val actividadMadre: Activity, val datos:ArrayList<Producto>) : RecyclerView.Adapter<ProductosViewHolder>() {
    var listener: OnItemClickListener? = null
    private lateinit var db: AppDataBase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return ProductosViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_catalogo,parent,false))
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto:Producto = datos.get(position)
        holder.nombre.text=producto.nombre
        holder.precio.text=""+producto.precio
        holder.cantidad.text=""+producto.cantidad

        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    db.productoDAO().delete(producto)
                }
            }
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.botonEditar.setOnClickListener {
            val intent: Intent = Intent(actividadMadre, ActividadEditarProducto::class.java)
            intent.putExtra("nombre", producto.nombre)
            actividadMadre.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    interface OnItemClickListener {
        fun onEliminarClick(producto: Producto)
    }

}
