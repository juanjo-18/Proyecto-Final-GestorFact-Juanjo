package recyclers.anadirProductosVenta

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.Producto
import com.example.proyectofinal.ActividadEditarVenta
import com.example.proyectofinal.R
import dataBase.AppDataBase
import recyclers.anadirProductosFactura.LineaFacturaViewHolder

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de linea venta.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo producto que se utilizarán para poblar la vista de la lista
 */
class LineaVentaAdapter(val actividadMadre: Activity, val datos: ArrayList<Producto>) :
    RecyclerView.Adapter<LineaVentaViewHolder>() {

    // Base de datos
    private lateinit var db: AppDataBase

    // Array para almacenar los textos escritos en los campos de texto
    private val escrito = arrayOfNulls<String>(datos.size)

    // Creación de la vista para cada elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineaVentaViewHolder {
        // Se inicializa la base de datos
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        // Se crea y retorna el ViewHolder
        return LineaVentaViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_anadir__venta,
                parent,
                false
            )
        )
    }

    // Asociación de los datos a cada elemento de la lista
    override fun onBindViewHolder(holder: LineaVentaViewHolder, position: Int) {
        // Se obtiene el producto en la posición correspondiente
        val producto: Producto = datos.get(position)
        // Se vincula el producto al ViewHolder para mostrar sus datos
        holder.bindProducto(datos[position])
        // Se establece el comportamiento del botón de borrar al pulsarlo
        holder.botonBorrar.setOnClickListener {
            // Se elimina el producto de la lista y se notifica al adaptador del cambio
            datos.removeAt(position)
            this.notifyItemRemoved(position)
            notifyItemRangeChanged(position, datos.size)
        }
    }

    // Devuelve la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return datos.size
    }

    // Devuelve el array con los textos escritos en los campos de texto
    fun getEscrito(): Array<String?> {
        return escrito
    }
}