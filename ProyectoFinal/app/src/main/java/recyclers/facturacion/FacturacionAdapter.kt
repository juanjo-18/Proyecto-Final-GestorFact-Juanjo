package recyclers.facturacion

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Factura
import com.example.proyectofinal.R

/**
 * Clase que extiende RecyclerView.Adapter para crear un adaptador personalizado
 * que se utiliza para poblar la vista de la lista de factura.
 *
 * @param actividadMadre instancia de la actividad principal que utiliza este adaptador
 * @param datos arreglo de elementos de tipo factura que se utilizarán para poblar la vista de la lista
 */
class FacturacionAdapter (val actividadMadre: Activity, val datos: ArrayList<Factura>):
    RecyclerView.Adapter<FacturacionViewHolder>() {
    /***
     * Este método crea una nueva instancia de la vista de elemento del adaptador
     * y devuelve un nuevo objeto FacturacionViewHolder que mantiene la referencia
     * de cada vista de elemento del adaptador.
     ***/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturacionViewHolder {
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
        holder.cobrada.text=""+factura.cobrada
        holder.precio.text=""+factura.precioTotal

        /***
         * Este listener se activa cuando se hace clic en el botón de borrar.
         * Elimina el elemento correspondiente de la lista de facturas y actualiza el RecyclerView.
         ***/
        holder.botonBorrar.setOnClickListener {
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        //hacer el boton editar funcional cuando tenga la actividad editar albaran
        /*
        holder.botonEditar.setOnClickListener {
            val intent: Intent = Intent(actividadMadre, ActividadEditarProducto::class.java)
            val bundle: Bundle = Bundle()
            bundle.putParcelable("producto", producto)
            intent.putExtras(bundle)
            actividadMadre.startActivity(intent)
        }*/
    }

    /***
     * Este método devuelve la cantidad total de elementos que se muestran en el RecyclerView.
     * Se utiliza para saber cuántos elementos hay en la lista de facturas.
     ***/
    override fun getItemCount(): Int {
        return datos.size
    }
}