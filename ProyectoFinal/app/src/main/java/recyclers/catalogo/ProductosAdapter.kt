package recyclers.catalogo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Producto
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import java.util.stream.Collectors

class ProductosAdapter (val actividadMadre: Activity, val datos:ArrayList<Producto>) : RecyclerView.Adapter<ProductosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        return ProductosViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_catalogo,parent,false))
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto:Producto = datos.get(position)
        holder.nombre.text=producto.nombre
        holder.precio.text=""+producto.precio
        holder.cantidad.text=""+producto.cantidad

        holder.botonBorrar.setOnClickListener {
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.botonEditar.setOnClickListener {
            val intent: Intent = Intent(actividadMadre, ActividadEditarProducto::class.java)
            val bundle: Bundle = Bundle()
            bundle.putParcelable("producto",producto)
            intent.putExtras(bundle)
            actividadMadre.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}
