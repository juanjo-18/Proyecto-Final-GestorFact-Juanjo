package recyclers.facturacion

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Factura
import com.example.proyectofinal.R


class FacturacionAdapter (val actividadMadre: Activity, val datos: ArrayList<Factura>):
    RecyclerView.Adapter<FacturacionViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturacionViewHolder {
            return FacturacionViewHolder(
                actividadMadre.layoutInflater.inflate(
                    R.layout.elementos_recycler_facturacion,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: FacturacionViewHolder, position: Int) {
            val factura: Factura = datos.get(position)
            holder.titulo.text=factura.titulo
            holder.nombreCliente.text=factura.nombreCliente
            holder.fecha.text= factura.fecha.toString()
            holder.cobrada.text=""+factura.cobrada
            holder.precio.text=""+factura.precioTotal


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

        override fun getItemCount(): Int {
            return datos.size
        }
}