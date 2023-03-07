package recyclers.anadirProductosVenta

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Producto
import com.example.proyectofinal.R

class LineaVentaAdapter (val actividadMadre: Activity, val datos:ArrayList<Producto>) : RecyclerView.Adapter<LineaVentaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineaVentaViewHolder {
        return LineaVentaViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_anadir__venta,parent,false))

    }



    override fun onBindViewHolder(holder: LineaVentaViewHolder, position: Int) {
        val item = datos[position]
        holder.campoNombre.setText(item.nombre.toString())
        holder.campoPrecio.setText(item.precio.toString())
        holder.campoCantidad.setText(item.cantidad.toString())
        holder.campoTotal.setText((item.cantidad*item.precio).toString())


        holder.botonBorrar.setOnClickListener {
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }

}