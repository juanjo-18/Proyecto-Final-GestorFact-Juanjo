package recyclers.albaranes

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Albaran
import clases.Producto
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import recyclers.catalogo.ProductosViewHolder

class AlbaranesAdapter(val actividadMadre: Activity, val datos: ArrayList<Albaran>) :
    RecyclerView.Adapter<AlbaranesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbaranesViewHolder {
        return AlbaranesViewHolder(
            actividadMadre.layoutInflater.inflate(
                R.layout.elementos_recycler_ventas,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbaranesViewHolder, position: Int) {
        val albaran: Albaran = datos.get(position)
        holder.titulo.text=albaran.titulo
        holder.nombreCliente.text=albaran.nombreCliente
        holder.fecha.text= albaran.fecha.toString()
        holder.estado.text=albaran.estado
        holder.precio.text=""+albaran.precioTotal


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