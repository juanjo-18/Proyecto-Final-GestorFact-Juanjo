package recyclers.facturacion

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import clases.Factura
import clases.Producto
import com.example.proyectofinal.ActividadEditarFactura
import com.example.proyectofinal.ActividadEditarVenta
import com.example.proyectofinal.R
import dataBase.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de factura.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo factura que se utilizarán para poblar la vista de la lista
 */
class FacturacionAdapter (val actividadMadre: Activity, var datos: ArrayList<Factura>):
    RecyclerView.Adapter<FacturacionViewHolder>() {
    private lateinit var db: AppDataBase

    /***
     * Este método crea una nueva instancia de la vista de elemento del adaptador
     * y devuelve un nuevo objeto FacturacionViewHolder que mantiene la referencia
     * de cada vista de elemento del adaptador.
     ***/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturacionViewHolder {
        db = Room.databaseBuilder(actividadMadre, AppDataBase::class.java, "db")
            .addMigrations(AppDataBase.MIGRATION_1_2)
            .build()
        return FacturacionViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_facturacion,
                parent,
                false
            )
        )
    }

    /***
     * Este método establece el contenido de cada elemento del RecyclerView.
     * Recibe como parámetros el objeto FacturacionViewHolder y la posición actual.
     * El contenido se establece utilizando los datos de la lista de facturas que se
     * pasó al constructor del adaptador.
     ***/
    override fun onBindViewHolder(holder: FacturacionViewHolder, position: Int) {
        val factura: Factura = datos.get(position)
        holder.titulo.text=factura.titulo
        holder.nombreCliente.text=factura.nombreCliente
        holder.fecha.text= factura.fecha.toString()
        if(factura.cobrada==true){
            holder.cobrada.text="Cobrada"
            val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto1)
            holder.cobrada.background = backgroundDrawable
        }else{
            holder.cobrada.text="Pendiente"
            val backgroundDrawable = ContextCompat.getDrawable(actividadMadre, R.drawable.bordes_redondos_texto)
            holder.cobrada.background = backgroundDrawable
        }
        holder.precio.text=""+factura.precioTotal

        /***
         * Este listener se activa cuando se hace clic en el botón de borrar.
         * Elimina el elemento correspondiente de la lista de facturas y actualiza el RecyclerView.
         ***/
        holder.botonBorrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                launch(Dispatchers.IO) {
                    // Se elimina la factura de la base de datos utilizando la función 'delete' de la clase 'FacturaDAO'
                    db.facturaDAO().delete(factura)
                }
            }
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }
        holder.botonEditar.setOnClickListener{
            // Se crea un intent para iniciar la actividad de editar factura, y se le pasan algunos datos
            val intent: Intent = Intent(actividadMadre, ActividadEditarFactura::class.java)
            intent.putExtra("titulo", factura.titulo)
            actividadMadre.startActivity(intent)
        }


    }

    /***
     * Este método devuelve la cantidad total de elementos que se muestran en el RecyclerView.
     * Se utiliza para saber cuántos elementos hay en la lista de facturas.
     ***/
    override fun getItemCount(): Int {
        return datos.size
    }
    fun filtrar(listaFiltrada: ArrayList<Factura>){
        this.datos=listaFiltrada
        notifyDataSetChanged()
    }

}