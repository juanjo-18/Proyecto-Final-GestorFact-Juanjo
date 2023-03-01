package recyclers.clientes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import clases.Cliente
import clases.Producto
import com.example.proyectofinal.ActividadEditarCliente
import com.example.proyectofinal.ActividadEditarProducto
import com.example.proyectofinal.R
import recyclers.catalogo.ProductosViewHolder

class ClientesAdapter (val actividadMadre: Activity, val datos:ArrayList<Cliente>) : RecyclerView.Adapter<ClientesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesViewHolder {
        return ClientesViewHolder(actividadMadre.layoutInflater.inflate(R.layout.elementos_recycler_clientes,parent,false))
    }

    override fun onBindViewHolder(holder: ClientesViewHolder, position: Int) {
        val cliente:Cliente = datos.get(position)
        holder.nombreCliente.text=cliente.nombre
        holder.referencia.text=cliente.referencia


        holder.botonBorrar.setOnClickListener {
            datos.removeAt(position)
            this.notifyDataSetChanged()
        }

        holder.botonEditar.setOnClickListener {
            val intent: Intent = Intent(actividadMadre, ActividadEditarCliente::class.java)
            val bundle: Bundle = Bundle()
            bundle.putParcelable("cliente",cliente)
            intent.putExtras(bundle)
            actividadMadre.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}