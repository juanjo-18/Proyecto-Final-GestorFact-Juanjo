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

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de productos.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo producto que se utilizarán para poblar la vista de la lista
 */
class ProductosAdapter (val actividadMadre: Activity, var datos:ArrayList<Producto>) : RecyclerView.Adapter<ProductosViewHolder>() {
    var listener: OnItemClickListener? = null
    private lateinit var db: AppDataBase


    // Crea una nueva vista de elemento de lista y la devuelve dentro de una instancia de ProductosViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        // Construye la base de datos
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        // Infla la vista del elemento de la lista en el ViewGroup padre.
        return ProductosViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_catalogo,parent,false))
    }

    // Reemplaza el contenido de una vista de elemento de la lista.
    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {

        // Obtiene el objeto Producto en la posición dada en la lista.
        val producto:Producto = datos.get(position)
        // Asigna los valores de las propiedades del producto a las vistas correspondientes en el ViewHolder.
        holder.nombre.text=producto.nombre
        holder.precio.text=""+producto.precio
        holder.cantidad.text=""+producto.cantidad

        // Define la acción del botón Borrar.
        holder.botonBorrar.setOnClickListener {
            // Elimina el producto de la base de datos en una corutina de un hilo separado.
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    db.productoDAO().delete(producto)
                }
            }
            // Elimina el producto de la lista y notifica al adaptador del cambio en la posición.
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        // Define la acción del botón Editar.
        holder.botonEditar.setOnClickListener {
            // Crea un intent para abrir la ActividadEditarProducto.
            val intent: Intent = Intent(actividadMadre, ActividadEditarProducto::class.java)
            // Agrega el nombre del producto como dato extra del intent.
            intent.putExtra("nombre", producto.nombre)
            // Inicia la actividad.
            actividadMadre.startActivity(intent)
        }
    }

    fun filtrar(listaFiltrada: ArrayList<Producto>){
        this.datos=listaFiltrada
        notifyDataSetChanged()
    }
    // Devuelve el número total de elementos en la lista.
    override fun getItemCount(): Int {
        return datos.size
    }

    // Define una interfaz para manejar la acción del botón Eliminar en la actividad principal.
    interface OnItemClickListener {
        fun onEliminarClick(producto: Producto)
    }

}