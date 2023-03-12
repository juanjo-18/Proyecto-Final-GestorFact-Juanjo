package recyclers.anadirProductosVenta

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Producto
import com.example.proyectofinal.R

class LineaVentaAdapter(val actividadMadre: Activity, val datos: ArrayList<Producto>) :
    RecyclerView.Adapter<LineaVentaViewHolder>() {


    private val escrito = arrayOfNulls<String>(datos.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineaVentaViewHolder {
        return LineaVentaViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_anadir__venta,
                parent,
                false
            )
        )

    }


    override fun onBindViewHolder(holder: LineaVentaViewHolder, position: Int) {

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